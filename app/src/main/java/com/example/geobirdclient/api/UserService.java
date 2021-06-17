package com.example.geobirdclient.api;

import com.example.geobirdclient.api.models.User.User;
import com.example.geobirdclient.api.models.User.UserDelete;
import com.example.geobirdclient.api.models.User.UserEdit;
import com.example.geobirdclient.api.models.User.UserLogin;
import com.example.geobirdclient.api.models.User.UserRegister;
import com.example.geobirdclient.api.models.User.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @GET("api/users")
    Call<List<User>> getUsers();

    @POST("api/user/register")
    Call<UserResponse> registerUser(@Body UserRegister userRegister);

    @POST("api/user/login")
    Call<UserResponse> loginUser(@Body UserLogin userLogin);

    @POST("api/user/delete")
    Call<UserResponse> deleteUser(@Body UserDelete userDelete);

    @POST("api/user/edit")
    Call<UserResponse> editUser(@Body UserEdit userEdit);

}
