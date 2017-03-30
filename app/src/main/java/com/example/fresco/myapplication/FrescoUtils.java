package com.example.fresco.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.fresco.myapplication.network.AddCookieInterceptor;
import com.example.fresco.myapplication.network.CustomCookieManager;
import com.example.fresco.myapplication.network.RetrieveCookieInterceptor;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;


/**
 * Created by  on 10/17/2016.
 * this links will be helpful if you want to have a deeper understanding of image
 * http://frescolib.org/docs/concepts.html
 */
public class FrescoUtils {


    private static final String TAG = "FrescoUtils" ;

    /*
        Get image from cache using this method
     */
    public Bitmap retrieveImageFromCache(Context context,  Uri imageUri){
       ImagePipeline imagePipeline = Fresco.getImagePipeline();
        final Bitmap[] downloadBitmap = new Bitmap[0];
        ImageRequest imageRequest = ImageRequestBuilder
               .newBuilderWithSource(imageUri)
               .setRequestPriority(Priority.HIGH)
               .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
               .build();

       DataSource<CloseableReference<CloseableImage>> dataSource =
               imagePipeline.fetchDecodedImage(imageRequest, context);

       try {
           dataSource.subscribe(new BaseBitmapDataSubscriber() {
               @Override
               public void onNewResultImpl(@Nullable Bitmap bitmap) {
                   if (bitmap == null) {
                       Log.d(TAG, "Bitmap data source returned success, but bitmap null.");
                       return;
                   }
                   downloadBitmap[0] =  bitmap;

               }

               @Override
               public void onFailureImpl(DataSource dataSource) {
                   // No cleanup required here
               }
           }, CallerThreadExecutor.getInstance());
       } finally {
           if (dataSource != null) {
               dataSource.close();
           }
       }
        return downloadBitmap[0];
    }

    /*
    We have given example for using OkHttpClient as backend
    Please use below  link to integrate Volley and OkHttp3
     a) https://github.com/facebook/fresco/tree/master/imagepipeline-backends
     b) http://frescolib.org/docs/using-other-network-layers.html
     */

    public void setOKHttpClient(Context context){

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(15000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(15000, TimeUnit.MILLISECONDS);
        okHttpClient.setCookieHandler(new CustomCookieManager());
        okHttpClient.interceptors().add(new AddCookieInterceptor());
        okHttpClient.interceptors().add(new RetrieveCookieInterceptor());

        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(context, okHttpClient)
        .build();
        Fresco.initialize(context, config);
    }

    /*
    Check to see if an item is in memory cache

     */
    public boolean isImageInCache(Uri uri){
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        return imagePipeline.isInBitmapMemoryCache(uri);

    }

    /*
   removing images from cache

    */
    public void removeImageFromCache(Uri uri){
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromCache(uri);

    }

    /*
    Clear cache based on business requirement
     */

    public void clearCache(){
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
    }

    /*
    http://frescolib.org/docs/resizing-rotating.html
    resizing limitation  :
      a) it only supports JPEG files
      b) the actual resize is carried out to the nearest 1/8 of the original size
     */
    public void resizeTheImage(Uri uri, SimpleDraweeView simpleDraweeView){
        int width = 50, height = 50;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        PipelineDraweeController controller =(PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(request)
                .build();
        simpleDraweeView.setController(controller);
    }

    /*
    makes the image rounded in behaviour
     */
    void setRoundCircle(SimpleDraweeView draweeView){
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(android.R.color.holo_blue_dark, 1.0f);
        roundingParams.setRoundAsCircle(true);
        draweeView.getHierarchy().setRoundingParams(roundingParams);

    }

    /*
    Using Thumbnail images
     */

    void getThumbNailImages(SimpleDraweeView simpleDraweeView,Uri uri){
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setLocalThumbnailPreviewsEnabled(true)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
    }

}
