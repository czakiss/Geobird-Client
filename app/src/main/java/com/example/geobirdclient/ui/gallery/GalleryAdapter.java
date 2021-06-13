package com.example.geobirdclient.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.geobirdclient.R;

import com.example.geobirdclient.api.models.Target.Target;
import com.example.geobirdclient.api.models.usertarget.UserTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GalleryAdapter extends BaseAdapter {

    private GalleryFragment mContext;
    private Context context;
    private List<Target> targets;
    private List<UserTarget> userTargets = new ArrayList<UserTarget>();


    public GalleryAdapter(Context context, List<Target> targets, List<UserTarget> userTargets) {
        this.context = context;
        this.targets =  targets;
        this.userTargets = userTargets;
    }

    @Override
    public int getCount() {
        return targets.size();
    }

    @Override
    public Target getItem(int position) {
         return this.targets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.targets.get(position).getId();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView = convertView;


        if(gridView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.item_target_layout,null);

            ImageView imageView = (ImageView) gridView.findViewById(R.id.targetImageView);
            TextView textView = (TextView) gridView.findViewById(R.id.textTarget);
            Integer id = targets.get(position).getId();
            List<UserTarget> checker = userTargets.stream().filter(x -> x.getIdTarget() == id).collect(Collectors.toList());
            if( checker.size() == 0){
                Glide.with(context).load(context.getResources().getDrawable(R.drawable.logo_bird)).into(imageView);

            } else{
                Glide.with(context).load(targets.get(position).getImage()).into(imageView);

            }
            textView.setText(targets.get(position).getName());

        }

        return gridView;
    }

    public Integer[] imageGallery = {
            R.drawable.ic_launcher_background
    };

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

}
