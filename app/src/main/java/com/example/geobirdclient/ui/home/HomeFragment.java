package com.example.geobirdclient.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.geobirdclient.R;
import com.example.geobirdclient.models.User.User;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button instructionButton;
    private Button informationsButton;
    private Button informationsButton2;
    private Button galleryButton;
    private Button yourAccountButton;
    private ImageView imageMainView;
    private Button adminEditable;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        instructionButton = root.findViewById(R.id.instructionButton);
        informationsButton = root.findViewById(R.id.informationsbutton);
        informationsButton2 = root.findViewById(R.id.informations_button_2);
        galleryButton = root.findViewById(R.id.galleryButton);
        yourAccountButton = root.findViewById(R.id.your_account_button);
        imageMainView = root.findViewById(R.id.lama_in_my_livingroom);
        adminEditable = root.findViewById(R.id.adminEditable);
        adminEditable.setVisibility(View.INVISIBLE);

        //TODO: dodac zalozenie pojawiajacego kafelka po zalogowaniu i znalezieniu ADMINA


        return root;
    }
}