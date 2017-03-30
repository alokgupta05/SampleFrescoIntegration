package com.example.fresco.myapplication.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;


/**
 * Created by  on 10/17/2016.
 */
public class AddCookieInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> preferences = new HashSet<String>();
        /*
            get values from preferences
         */
     //   HashSet<String> preferences = (HashSet) PreferenceHelper.getDefaultPreferences().getStringSet(PreferenceHelper.PREF_BOX_COOKIES, new HashSet<String>());
        int count = 1;
        for (String cookie : preferences) {
            builder.addHeader("Cookie" + count, cookie);
            count++;
        }

        return chain.proceed(builder.build());
    }
}