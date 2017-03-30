package com.example.fresco.myapplication;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity{
    public List<String> mStringArrayList = new ArrayList<String>();
    String mStringArray[] = { "http://i.imgur.com/tGbaZCY.jpg",
            "http://i.imgur.com/jlFgGpe.jpg","http://i.imgur.com/YCq7IyG.jpg","http://i.imgur.com/w8wgAQX.jpg",
            "http://i.imgur.com/ZP23iGG.jpg","http://i.imgur.com/A1UIVOu.png","http://i.imgur.com/Rit8sdG.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayList();
        ListView listView = (ListView) findViewById(R.id.listView);
        ImageViewAdapter imageViewAdapter = new ImageViewAdapter(this,mStringArrayList);
        listView.setAdapter(imageViewAdapter);
    }

    void populateArrayList(){
        List listImages =Arrays.asList(mStringArray);
        mStringArrayList.addAll(listImages);
        mStringArrayList.addAll(listImages);
        mStringArrayList.addAll(listImages);
        mStringArrayList.addAll(listImages);


    }


}
