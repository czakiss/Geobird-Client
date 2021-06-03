package com.example.geobirdclient.api;

import com.example.geobirdclient.models.User.User;
import com.example.geobirdclient.models.User.UserDelete;
import com.example.geobirdclient.models.User.UserLogin;
import com.example.geobirdclient.models.User.UserRegister;
import com.example.geobirdclient.models.User.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @GET("api/users")
    Call<List<User>> getUsers();

    @POST("api/user/register")
    Call<UserResponse> registerUser(@Body UserRegister userRegister);

    @POST("api/user/login")
    Call<UserResponse> loginUser(@Body UserLogin userLogin);

    @POST("api/user/delete")
    Call<UserResponse> deleteUser(@Body UserDelete userDelete);
}
