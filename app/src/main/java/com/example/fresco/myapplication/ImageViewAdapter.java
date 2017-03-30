package com.example.fresco.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 8/26/2016.
 */
public class ImageViewAdapter extends BaseAdapter{
    private Context context;
    private List<String> stringList = new ArrayList<String>();
    ImageViewAdapter(Context context, List<String> stringArrayList){
        this.context = context;
        stringList = stringArrayList;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item,null);
        }

        Uri imageUri = Uri.parse(stringList.get(position));
        SimpleDraweeView draweeView = (SimpleDraweeView) convertView.findViewById(R.id.my_image_view);
        draweeView.setImageURI(imageUri);
        return convertView;
    }


}
