package com.example.geobirdclient.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery,container,false);
        activity = getActivity();
        modalWidgets = new ModalWidgets(activity);

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
        return root;
    }

}
