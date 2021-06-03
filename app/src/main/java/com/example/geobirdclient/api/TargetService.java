package com.example.geobirdclient.api;

import com.example.geobirdclient.models.Target.Target;
import com.example.geobirdclient.models.Target.TargetDelete;
import com.example.geobirdclient.models.Target.TargetRegister;
import com.example.geobirdclient.models.Target.TargetResponse;
import com.example.geobirdclient.models.Target.TargetUpdate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TargetService {
    @GET("api/targets")
    Call<List<Target>> getTargets();

    @POST("api/target/register")
    Call<TargetResponse> registerTarget(@Body TargetRegister targetRegister);

    @POST("api/target/update")
    Call<TargetResponse> updateTarget(@Body TargetUpdate targetUpdate);

    @POST("api/target/delete")
    Call<TargetResponse> deleteTarget(@Body TargetDelete targetDelete);
}
