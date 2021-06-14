package com.example.geobirdclient.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.geobirdclient.api.models.Target.TargetGetResponse;
import com.example.geobirdclient.api.models.User.User;
import com.example.geobirdclient.api.models.User.UserLogin;
import com.example.geobirdclient.api.models.User.UserResponse;
import com.example.geobirdclient.api.models.usertarget.UserTarget;
import com.example.geobirdclient.api.models.usertarget.UserTargetGetByResponse;
import com.example.geobirdclient.api.models.usertarget.UserTargetGetByUser;
import com.example.geobirdclient.ui.auth.ModalWidgets;

import java.util.ArrayList;
import java.util.List;
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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery,container,false);
        activity = getActivity();
        modalWidgets = new ModalWidgets(activity);

        targetLayout = root.findViewById(R.id.targetLayout);
        targetImage = root.findViewById(R.id.targetImage);
        targetDesc = root.findViewById(R.id.targetDescription);
        targetTitle = root.findViewById(R.id.targetTitle);
        checkClickedPosition();

        gridView = (GridView) root.findViewById(R.id.grid_view_gallery);



        UserTargetService serviceUTarget = Api.getRetrofit().create(UserTargetService.class);
        Call<UserTargetGetByResponse> callUTarget = serviceUTarget.getUserTargetByUser(new UserTargetGetByUser(MainActivity.currentUser.getId()));
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
                checkClickedPosition();
                Target target = (Target) parent.getItemAtPosition(position);
                System.out.println(target);

                List<UserTarget> checker = targetList.stream().filter(x -> x.getIdTarget() == id).collect(Collectors.toList());
                if( checker.size() == 0){
                    Glide.with(activity).load(activity.getResources().getDrawable(R.drawable.logo_bird)).into(targetImage);

                } else{
                    Glide.with(activity).load(targets.get(position).getImage()).into(targetImage);

                }
                targetDesc.setText(target.getDescription());
                targetTitle.setText(target.getName());



            }
        });
        return root;
    }

    private void checkClickedPosition(){
        if (position > -1){
            targetLayout.setVisibility(View.VISIBLE);
        } else {
            targetLayout.setVisibility(View.GONE);
        }
    }
}
