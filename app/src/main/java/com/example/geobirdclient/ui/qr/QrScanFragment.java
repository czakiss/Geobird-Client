package com.example.geobirdclient.ui.qr;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.bumptech.glide.Glide;
import com.example.geobirdclient.MainActivity;
import com.example.geobirdclient.R;
import com.example.geobirdclient.api.Api;
import com.example.geobirdclient.api.TargetService;
import com.example.geobirdclient.api.UserTargetService;
import com.example.geobirdclient.api.models.Target.Target;
import com.example.geobirdclient.api.models.Target.TargetGet;
import com.example.geobirdclient.api.models.Target.TargetGetResponse;
import com.example.geobirdclient.api.models.usertarget.UserTargetAddDTO;
import com.example.geobirdclient.api.models.usertarget.UserTargetAddResponseDTO;
import com.example.geobirdclient.ui.auth.ModalWidgets;
import com.google.zxing.Result;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QrScanFragment extends Fragment {

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private CodeScanner mCodeScanner;
    private View root;
    private QrScanViewModel qrScanViewModel;
    private Button catchButton;
    private TextView statusTextView;
    private ImageView targetImageView;
    private View infoLayout;
    private View statusLayout;
    private Target target;
    private Activity activity;
    ModalWidgets modalWidgets;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        qrScanViewModel = new ViewModelProvider(this).get(QrScanViewModel.class);
        root = inflater.inflate(R.layout.fragment_qr_scan, container, false);

        activity = getActivity();
        modalWidgets = new ModalWidgets(activity);

        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        catchButton = root.findViewById(R.id.catchButton);
        statusTextView = root.findViewById(R.id.statusTextView);
        targetImageView = root.findViewById(R.id.targetImageView);
        infoLayout = root.findViewById(R.id.infoLayout);
        statusLayout = root.findViewById(R.id.statusLayout);


        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.CAMERA},
                MY_PERMISSIONS_REQUEST_CAMERA);

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
        } else {
            mCodeScanner = new CodeScanner(activity, scannerView);
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TargetService service = Api.getRetrofit().create(TargetService.class);
                            Call<TargetGetResponse> call = service.getTarget(new TargetGet(result.getText()));
                            call.enqueue(new Callback<TargetGetResponse>() {
                                @Override
                                public void onResponse(Call<TargetGetResponse> call, Response<TargetGetResponse> response) {
                                    System.out.println(response.code());

                                    TargetGetResponse targetResponse= response.body();
                                    if(targetResponse != null){
                                        target =  targetResponse.getTargetDataDTO();
                                        System.out.println(target);
                                        Glide.with(activity).load(target.getImage()).into(targetImageView);
                                        statusTextView.setText(target.getName());
                                        infoLayout.setVisibility(View.GONE);
                                        statusLayout.setVisibility(View.VISIBLE);
                                    } else {
                                        modalWidgets.showToast(getString(R.string.incorrect_qr_code));
                                    }
                                }

                                @Override
                                public void onFailure(Call<TargetGetResponse> call, Throwable t) {
                                    Toast.makeText(activity, "Something goes wrong: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            });



                        }
                    });
                }
            });
            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });
        }

        addActions();
        return root;
    }

    private void addActions() {
        catchButton.setOnClickListener(view -> {

            UserTargetService service = Api.getRetrofit().create(UserTargetService.class);
            Call<UserTargetAddResponseDTO> call = service.addUserTargets(new UserTargetAddDTO(MainActivity.currentUser.getId(),target.getId()));
            call.enqueue(new Callback<UserTargetAddResponseDTO>() {

                @Override
                public void onResponse(Call<UserTargetAddResponseDTO> call, Response<UserTargetAddResponseDTO> response) {
                    if(response.code()==200){
                        Toast.makeText(activity, getString(R.string.qr_code_added_target),Toast.LENGTH_LONG).show();

                    } else  if (response.code()==409){
                        Toast.makeText(activity, getString(R.string.qr_code_exists_target),Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(activity, getString(R.string.error),Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<UserTargetAddResponseDTO> call, Throwable t) {
                    modalWidgets.showToast(getString(R.string.error));
                }
            });

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if(mCodeScanner != null){
            mCodeScanner.startPreview();
        }
    }

    @Override
    public void onPause() {
        if(mCodeScanner != null){
            mCodeScanner.releaseResources();
        }

        super.onPause();
    }

}