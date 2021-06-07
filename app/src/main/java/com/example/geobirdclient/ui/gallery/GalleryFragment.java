package com.example.geobirdclient.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.geobirdclient.R;
import com.example.geobirdclient.api.models.Target.Target;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GridView galleryGridView;
    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery,container,false);

        Target[] targetList;

        ArrayAdapter<Target> cheeseAdapter =
                new ArrayAdapter<Target>(getActivity(),
                        R.layout.item_character_gallery,
                        R.id.image_target,
                        R.id.title_name_target,
                        targetList
                );

        galleryGridView = root.findViewById(R.id.grid_view_gallery);
        galleryGridView.setLayout
        galleryGridView.setLayoutManager(new LinearLayoutManager(root.getContext()));
       // galleryGridView.setHasFixedSize(true);

        //TODO: CHECK THIS CHECK THIS !!
        //new TargetService().getTargets(this, MainActivity.currentUser);

        return root;
    }

    public void drawGallery(Target[] targets) {
        GalleryAdapter targetAdapter = new GalleryAdapter(targets);
        galleryGridView.setAdapter(targetAdapter);
        galleryGridView.setVisibility(View.VISIBLE);
    }


}
