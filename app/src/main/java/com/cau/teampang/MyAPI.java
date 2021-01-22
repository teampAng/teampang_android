package com.cau.teampang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAPI {
    @POST("/logins/")
    Call<LoginData>post_logins(@Body LoginData login);

    @PATCH("logins/{pk}/")
    Call<LoginData> patch_logins(@Path("pk") int pk,@Body LoginData login);

    @DELETE("/logins/{pk}/")
    Call<LoginData> delete_logins(@Path("pk") int pk);

    @GET("/logins/")
    Call<List<LoginData>> get_logins();

    @GET("/logins/{pk}/")
    Call<LoginData> get_logins_pk(@Path("pk") int pk);


}
