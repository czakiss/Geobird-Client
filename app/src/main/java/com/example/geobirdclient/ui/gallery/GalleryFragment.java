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


import com.example.geobirdclient.MainActivity;
import com.example.geobirdclient.R;
import com.example.geobirdclient.api.Api;
import com.example.geobirdclient.api.UserService;
import com.example.geobirdclient.api.UserTargetService;
import com.example.geobirdclient.api.models.Target.Target;
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

    private GridView galleryGridView;
    private GalleryViewModel galleryViewModel;
    private List<UserTarget> targetList = new ArrayList<>();
    private ModalWidgets modalWidgets = new ModalWidgets(getActivity());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery,container,false);


        UserTargetService service = Api.getRetrofit().create(UserTargetService.class);
        Call<UserTargetGetByResponse> call = service.getUserTargetByUser(new UserTargetGetByUser(MainActivity.currentUser.getId()));
        call.enqueue(new Callback<UserTargetGetByResponse>() {
            @Override
            public void onResponse(Call<UserTargetGetByResponse> call, Response<UserTargetGetByResponse> response) {
                UserTargetGetByResponse userTargetGetByResponse = response.body();
                if(userTargetGetByResponse !=null){
                    targetList = userTargetGetByResponse.getUserTargetDataDTO();
                    System.out.println("zaladowano: " + targetList.toString());
                } else {
                    modalWidgets.showToast(getString(R.string.incorrect_login_data));
                }
            }

            @Override
            public void onFailure(Call<UserTargetGetByResponse> call, Throwable t) {
                System.out.println("Cos poszlo nie tak: "+ t.getMessage());
            }
        });

        ArrayAdapter<Target> cheeseAdapter =
                new ArrayAdapter<Target>(getActivity(),
                        R.layout.item_character_gallery,
                        R.id.image_target,
                        R.id.title_name_target,
                        targetList[0]
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
