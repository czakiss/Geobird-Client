package com.example.geobirdclient.api;

import com.example.geobirdclient.models.Target.Target;
import com.example.geobirdclient.models.Target.TargetDelete;
import com.example.geobirdclient.models.Target.TargetRegister;
import com.example.geobirdclient.models.Target.TargetResponse;
import com.example.geobirdclient.models.Target.TargetUpdate;
import com.example.geobirdclient.models.usertarget.UserTarget;
import com.example.geobirdclient.models.usertarget.UserTargetAdd;
import com.example.geobirdclient.models.usertarget.UserTargetAddResponse;
import com.example.geobirdclient.models.usertarget.UserTargetDrop;
import com.example.geobirdclient.models.usertarget.UserTargetDropByTarget;
import com.example.geobirdclient.models.usertarget.UserTargetDropByUser;
import com.example.geobirdclient.models.usertarget.UserTargetDropResponse;
import com.example.geobirdclient.models.usertarget.UserTargetGet;
import com.example.geobirdclient.models.usertarget.UserTargetGetByResponse;
import com.example.geobirdclient.models.usertarget.UserTargetGetByTarget;
import com.example.geobirdclient.models.usertarget.UserTargetGetByUser;
import com.example.geobirdclient.models.usertarget.UserTargetGetResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserTargetService {
    @GET("api/usertargets")
    Call<List<UserTarget>> getUserTargets();

    @POST("api/usertargets/add")
    Call<UserTargetAddResponse> addUserTargets(@Body UserTargetAdd userTargetAdd);

    @POST("api/usertargets/remove")
    Call<UserTargetDropResponse> dropUserTarget(@Body UserTargetDrop userTargetDrop);

    @POST("api/usertargets/removebyuser")
    Call<UserTargetDropResponse> dropUserTargetByUser(@Body UserTargetDropByUser userTargetDropByUser);

    @POST("api/usertargets/removebytarget")
    Call<UserTargetDropResponse> dropUserTargetByTarget(@Body UserTargetDropByTarget userTargetDropByTarget);

    @POST("api/usertargets/get")
    Call<UserTargetGetResponse> getUserTarget(@Body UserTargetGet userTargetGet);

    @POST("api/usertargets/get")
    Call<UserTargetGetByResponse> getUserTargetByUser(@Body UserTargetGetByUser userTargetGetByUser);

    @POST("api/usertargets/get")
    Call<UserTargetGetByResponse> getUserTargetByTarget(@Body UserTargetGetByTarget userTargetGetByTarget);

}


