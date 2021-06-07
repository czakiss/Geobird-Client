package com.example.geobirdclient.ui.gallery;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.geobirdclient.R;
import com.example.geobirdclient.api.TargetService;
import com.example.geobirdclient.api.models.Target.Target;
import com.example.geobirdclient.api.models.Target.TargetDelete;
import com.example.geobirdclient.api.models.Target.TargetRegister;
import com.example.geobirdclient.api.models.Target.TargetResponse;
import com.example.geobirdclient.api.models.Target.TargetUpdate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class GalleryAdapter extends BaseAdapter {

    private Context mContext;
    private List<Target> localDataSet;

    public GalleryAdapter(GalleryFragment mContext) {
        this.mContext = mContext;
    }
    //TODO: ALSO THIS CHECK!!!

    public GalleryAdapter(Target[] targets) {
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(85,85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        }else{
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(imageGallery[position]);
        //TODO: dodac liste z target

        return imageView;
    }

    public Integer[] imageGallery = {
            R.drawable.ic_launcher_background
    };

    /*public GalleryAdapter(Target[] dataSet){
        List<Target> data = new ArrayList<>();
        for (int i = 0; i < dataSet.length; i++) {
            data.add(dataSet[i]);
        }
        localDataSet = data;
    }*/
}
