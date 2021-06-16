package com.example.geobirdclient.ui.gallery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.bumptech.glide.Glide;
import com.example.geobirdclient.MainActivity;
import com.example.geobirdclient.R;
import com.example.geobirdclient.api.Api;
import com.example.geobirdclient.api.TargetService;
import com.example.geobirdclient.api.UserService;
import com.example.geobirdclient.api.UserTargetService;
import com.example.geobirdclient.api.models.Target.Target;
import com.example.geobirdclient.api.models.Target.TargetDelete;
import com.example.geobirdclient.api.models.Target.TargetGetResponse;
import com.example.geobirdclient.api.models.Target.TargetRegister;
import com.example.geobirdclient.api.models.Target.TargetResponse;
import com.example.geobirdclient.api.models.Target.TargetUpdate;
import com.example.geobirdclient.api.models.User.User;
import com.example.geobirdclient.api.models.User.UserLogin;
import com.example.geobirdclient.api.models.User.UserResponse;
import com.example.geobirdclient.api.models.usertarget.UserTarget;
import com.example.geobirdclient.api.models.usertarget.UserTargetAddDTO;
import com.example.geobirdclient.api.models.usertarget.UserTargetAddResponseDTO;
import com.example.geobirdclient.api.models.usertarget.UserTargetGetByResponse;
import com.example.geobirdclient.api.models.usertarget.UserTargetGetByUser;
import com.example.geobirdclient.ui.auth.ModalWidgets;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {

    private GridView gridView;
    private List<UserTarget> targetList = new ArrayList<UserTarget>();
    List<Target> targets;
    private ModalWidgets modalWidgets = new ModalWidgets(getActivity());
    private Activity activity;
    Fragment fragment = this;
    private LinearLayout targetLayout;
    private ImageView targetImage;
    private TextView targetDesc;
    private TextView targetTitle;
    private Integer position = -1;
    private Button buttonAddTarget;
    private Button buttonDeleteTarget;
    private Button buttonEditTarget;
    private LinearLayout linearLayoutAdmin;
    private TextInputLayout codeTextInputLayout;
    private TextInputLayout nameTextInputLayout;
    private TextInputLayout descriptionTextInputLayout;
    private TextInputLayout pointsTextInputLayout;
    private TextInputLayout imageTextInputLayout;
    private TextInputLayout latitudeTextInputLayout;
    private TextInputLayout longitudeInputLayout;

    private String code;
    private String description;
    private String name;
    private String image;
    private Integer points;
    private Double longitude;
    private Double latitude;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery,container,false);
        activity = getActivity();
        modalWidgets = new ModalWidgets(activity);

        targetLayout = root.findViewById(R.id.targetLayout);
        targetImage = root.findViewById(R.id.targetImage);
        targetDesc = root.findViewById(R.id.targetDescription);
        targetTitle = root.findViewById(R.id.targetTitle);
        linearLayoutAdmin = root.findViewById(R.id.linearLayoutAdmin);

        codeTextInputLayout = root.findViewById(R.id.codeTextField);
        nameTextInputLayout = root.findViewById(R.id.nameTextField);
        descriptionTextInputLayout = root.findViewById(R.id.descriptionTextField);
        pointsTextInputLayout = root.findViewById(R.id.pointsTextField);
        imageTextInputLayout = root.findViewById(R.id.imageTextField);
        latitudeTextInputLayout = root.findViewById(R.id.latitudeTextField);
        longitudeInputLayout = root.findViewById(R.id.longitudeTextField);

        buttonAddTarget = root.findViewById(R.id.buttonAddTarget);
        buttonDeleteTarget = root.findViewById(R.id.buttonDeleteTarget);
        buttonEditTarget = root.findViewById(R.id.buttonEditTarget);

        checkClickedPosition();

        gridView = (GridView) root.findViewById(R.id.grid_view_gallery);

        TargetService targetService = Api.getRetrofit().create(TargetService.class);


        buttonAddTarget.setOnClickListener(e->{

            checkFields();
            Call<TargetResponse> userTargetAddResponseDTOCall = targetService.registerTarget(new TargetRegister(
                code,name,description,points,image,latitude,longitude
            ));
            userTargetAddResponseDTOCall.enqueue(new Callback<TargetResponse>(){

                @Override
                public void onResponse(Call<TargetResponse> call, Response<TargetResponse> response) {
                    TargetResponse targetResponse = response.body();
                    if (targetResponse == null){
                        modalWidgets.showToast(getString(R.string.error));
                        System.out.println(response.message());
                        System.out.println(call.hashCode());
                        System.out.println("Problem not added target");
                    } else {
                        modalWidgets.showToast(getString(R.string.register_successful));
                        loadTargets();
                    }
                }

                @Override
                public void onFailure(Call<TargetResponse> call, Throwable t) {
                    modalWidgets.showToast(getString(R.string.error));
                    System.out.println(call.hashCode());
                    System.out.println(t.getMessage());

                }
            });
        });

        buttonDeleteTarget.setOnClickListener(e->{

            checkFields();
            Call<TargetResponse> userTargetAddResponseDTOCall = targetService.deleteTarget(new TargetDelete(code));
            userTargetAddResponseDTOCall.enqueue(new Callback<TargetResponse>(){

                @Override
                public void onResponse(Call<TargetResponse> call, Response<TargetResponse> response) {
                    TargetResponse targetResponse = response.body();
                    if (targetResponse == null){
                        modalWidgets.showToast(getString(R.string.error));
                        System.out.println(response.message());
                        System.out.println(call.hashCode());
                        System.out.println("Problem not deleted target");
                    } else {
                        modalWidgets.showToast(getString(R.string.delete_target));
                        loadTargets();
                    }
                }

                @Override
                public void onFailure(Call<TargetResponse> call, Throwable t) {
                    modalWidgets.showToast(getString(R.string.error));
                    System.out.println(call.hashCode());
                    System.out.println(t.getMessage());

                }
            });
        });

        buttonEditTarget.setOnClickListener(e->{

            checkFields();
            Call<TargetResponse> userTargetAddResponseDTOCall = targetService.updateTarget(new TargetUpdate(code,name,description,points,image,latitude,longitude));
            userTargetAddResponseDTOCall.enqueue(new Callback<TargetResponse>(){

                @Override
                public void onResponse(Call<TargetResponse> call, Response<TargetResponse> response) {
                    TargetResponse targetResponse = response.body();
                    if (targetResponse == null){
                        modalWidgets.showToast(getString(R.string.error));
                        System.out.println(response.message());
                        System.out.println(call.hashCode());
                        System.out.println("Problem not update target");
                    } else {
                        modalWidgets.showToast(getString(R.string.update_target));
                        loadTargets();
                    }
                }

                @Override
                public void onFailure(Call<TargetResponse> call, Throwable t) {
                    modalWidgets.showToast(getString(R.string.error));
                    System.out.println(call.hashCode());
                    System.out.println(t.getMessage());

                }
            });
        });

        loadTargets();
        return root;
    }

    @SuppressLint("SetTextI18n")
    private void checkClickedPosition(){
        if (position > -1){
            targetLayout.setVisibility(View.VISIBLE);
            System.out.println("permisja: " + MainActivity.currentUser.getPermission());
            if(MainActivity.currentUser.getPermission() == 1){
                linearLayoutAdmin.setVisibility(View.VISIBLE);

                Target target = targets.get(position);
                Objects.requireNonNull(codeTextInputLayout.getEditText()).setText(target.getCode());
                Objects.requireNonNull(nameTextInputLayout.getEditText()).setText(target.getName());
                Objects.requireNonNull(descriptionTextInputLayout.getEditText()).setText(target.getDescription());
                Objects.requireNonNull(pointsTextInputLayout.getEditText()).setText(target.getPoints().toString());
                Objects.requireNonNull(imageTextInputLayout.getEditText()).setText(target.getImage());
                Objects.requireNonNull(latitudeTextInputLayout.getEditText()).setText(Double.toString(target.getLocX()));
                Objects.requireNonNull(longitudeInputLayout.getEditText()).setText(Double.toString(target.getLocY()));
            } else {
                linearLayoutAdmin.setVisibility(View.GONE);
            }
        } else {
            targetLayout.setVisibility(View.GONE);
            linearLayoutAdmin.setVisibility(View.GONE);
        }
    }

    private void loadTargets(){
        UserTargetService userTargetService = Api.getRetrofit().create(UserTargetService.class);

        Call<UserTargetGetByResponse> callUTarget = userTargetService.getUserTargetByUser(new UserTargetGetByUser(MainActivity.currentUser.getId()));
        callUTarget.enqueue(new Callback<UserTargetGetByResponse>() {
            @Override
            public void onResponse(Call<UserTargetGetByResponse> call, Response<UserTargetGetByResponse> response) {
                UserTargetGetByResponse userTargetGetByResponse = response.body();
                if(userTargetGetByResponse !=null){
                    targetList = userTargetGetByResponse.getUserTargetDataDTOS();

                    TargetService serviceTarget = Api.getRetrofit().create(TargetService.class);
                    Call<List<Target>> callTarget = serviceTarget.getTargets();
                    callTarget.enqueue(new Callback<List<Target>>() {
                        @Override
                        public void onResponse(Call<List<Target>> call, Response<List<Target>> response) {
                            targets = response.body();
                            if(targets !=null){
                                System.out.println("zaladowano: " + targets);

                                for (Target target: targets
                                     ) {
                                    System.out.println("test id" + target.getId());
                                }

                                for (UserTarget target: targetList
                                ) {
                                    System.out.println("test uid" + target.getIdUser() + "test id" + target.getIdTarget());
                                }
                                GalleryAdapter galleryAdapter = new GalleryAdapter(fragment.getContext(),targets, targetList);

                                gridView.setAdapter(galleryAdapter);

                            } else {
                                System.out.println(response.message());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Target>> call, Throwable t) {
                            modalWidgets.showToast(getString(R.string.error));
                        }
                    });

                } else {
                    modalWidgets.showToast(getString(R.string.error));
                    System.out.println(response.message());
                }
            }
            @Override
            public void onFailure(Call<UserTargetGetByResponse> call, Throwable t) {
                System.out.println("Cos poszlo nie tak: "+ t.getMessage());
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int p, long id) {
                System.out.println(position);
                position = p;
                System.out.println("pozycja: " + position);
                checkClickedPosition();
                Target target = (Target) parent.getItemAtPosition(position);
                System.out.println(target);

                List<UserTarget> checker = targetList.stream().filter(x -> x.getIdTarget() == id).collect(Collectors.toList());
                System.out.println("userp:" + MainActivity.currentUser.getPermission());
                if( checker.size() == 0 && MainActivity.currentUser.getPermission() == 0 ){
                    Glide.with(activity).load(activity.getResources().getDrawable(R.drawable.logo_bird)).into(targetImage);

                } else{
                    Glide.with(activity).load(targets.get(position).getImage()).into(targetImage);

                }
                targetDesc.setText(target.getDescription());
                targetTitle.setText(target.getName());
            }
        });
    }

    private boolean checkFields(){
        boolean check = true;
        code = codeTextInputLayout.getEditText().getText().toString();
        name = nameTextInputLayout.getEditText().getText().toString();
        description = descriptionTextInputLayout.getEditText().getText().toString();
        try {
            points = Integer.valueOf(pointsTextInputLayout.getEditText().getText().toString());
        }  catch (NumberFormatException ex){
            points = 0;
            pointsTextInputLayout.setError("Points is empty");
            check = false;
        }
        image = imageTextInputLayout.getEditText().getText().toString();
        try {
            latitude = Double.valueOf(latitudeTextInputLayout.getEditText().getText().toString());
        }  catch (NumberFormatException ex){
            latitude = 0.0;
            latitudeTextInputLayout.setError("Latitude is empty");
            check = false;
        }
        try {
            longitude = Double.valueOf(longitudeInputLayout.getEditText().getText().toString());
        }  catch (NumberFormatException ex){
            longitude = 0.0;
            longitudeInputLayout.setError("Longitude is empty");
            check = false;
        }

        if(code.equals("")){
            codeTextInputLayout.setError("Code is empty");
            check = false;
        }
        if(name.equals("")){
            nameTextInputLayout.setError("Name is empty");
            check = false;
        }
        if(description.equals("")){
            descriptionTextInputLayout.setError("Description is empty");
            check = false;
        }

        if(image.equals("")){
            imageTextInputLayout.setError("Image is empty");
            check = false;
        }
        return check;
    }
}
