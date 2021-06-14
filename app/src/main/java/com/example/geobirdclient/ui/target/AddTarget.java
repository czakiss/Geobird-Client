package com.example.geobirdclient.ui.target;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.geobirdclient.R;
import com.example.geobirdclient.ui.auth.ModalWidgets;

public class AddTarget extends Fragment {
    private EditText nameTarget;
    private EditText descriptionTarget;
    private EditText pointsTarget;
    private EditText imageURLTarget;
    private EditText codeTarget;
    private EditText locX;
    private EditText locY;
    private Button saveTarget;
    private View root;
    private Activity activity;
    ModalWidgets modalWidgets;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_add_target, container, false);

        activity = getActivity();
        modalWidgets = new ModalWidgets(activity);

        nameTarget = root.findViewById(R.id.name_target_edit_text);
        descriptionTarget = root.findViewById(R.id.description_target_edit_text);
        pointsTarget = root.findViewById(R.id.pionts_target_edit_text);
        imageURLTarget = root.findViewById(R.id.image_url_target_edit_text);
        codeTarget = root.findViewById(R.id.code_target_edit_text);
        locX = root.findViewById(R.id.loc_x_target_edit_text);
        locY = root.findViewById(R.id.loc_y_target_edit_text);
        saveTarget = root.findViewById(R.id.add_target_button);

        addAction();

        return root;
    }

    private void addAction() {
        saveTarget.setOnClickListener(view ->{

        });


    }


}
