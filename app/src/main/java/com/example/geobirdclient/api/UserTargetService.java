package com.example.geobirdclient.api;

import com.example.geobirdclient.api.models.usertarget.UserTarget;
import com.example.geobirdclient.api.models.usertarget.UserTargetAddDTO;
import com.example.geobirdclient.api.models.usertarget.UserTargetAddResponseDTO;
import com.example.geobirdclient.api.models.usertarget.UserTargetDrop;
import com.example.geobirdclient.api.models.usertarget.UserTargetDropByTarget;
import com.example.geobirdclient.api.models.usertarget.UserTargetDropByUser;
import com.example.geobirdclient.api.models.usertarget.UserTargetDropResponse;
import com.example.geobirdclient.api.models.usertarget.UserTargetGet;
import com.example.geobirdclient.api.models.usertarget.UserTargetGetByResponse;
import com.example.geobirdclient.api.models.usertarget.UserTargetGetByTarget;
import com.example.geobirdclient.api.models.usertarget.UserTargetGetByUser;
import com.example.geobirdclient.api.models.usertarget.UserTargetGetResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserTargetService {
    @GET("api/usertargets")
    Call<List<UserTarget>> getUserTargets();

    @POST("api/usertargets/add")
    Call<UserTargetAddResponseDTO> addUserTargets(@Body UserTargetAddDTO userTargetAddDTO);

    @POST("api/usertargets/remove")
    Call<UserTargetDropResponse> dropUserTarget(@Body UserTargetDrop userTargetDrop);

    @POST("api/usertargets/removebyuser")
    Call<UserTargetDropResponse> dropUserTargetByUser(@Body UserTargetDropByUser userTargetDropByUser);

    @POST("api/usertargets/removebytarget")
    Call<UserTargetDropResponse> dropUserTargetByTarget(@Body UserTargetDropByTarget userTargetDropByTarget);

    @POST("api/usertargets/get")
    Call<UserTargetGetResponse> getUserTarget(@Body UserTargetGet userTargetGet);

    @POST("api/usertargets/getbyuser")
    Call<UserTargetGetByResponse> getUserTargetByUser(@Body UserTargetGetByUser userTargetGetByUser);

    @POST("api/usertargets/getbytarget")
    Call<UserTargetGetByResponse> getUserTargetByTarget(@Body UserTargetGetByTarget userTargetGetByTarget);

}


