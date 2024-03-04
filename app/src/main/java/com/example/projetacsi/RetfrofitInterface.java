package com.example.projetacsi;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetfrofitInterface {

    // transfert data from java to register function in node js
    @POST("api/RegisterUser")
    Call<Void> executeSignup (@Body HashMap<String, String> map);
    @POST("api/LoginUser")
    Call<Void> executelogin (@Body HashMap<String, String> map);
}
