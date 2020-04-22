package com.example.d_safe.ui;

import android.database.Observable;

import com.example.d_safe.ui.home.Invitation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataService {
    @GET("/interactions")
    Call<List<Invitation>> getAll();

    @POST("/interactions")
    @FormUrlEncoded
    Call<Integer> savePost(@Field("eventId") int eventId,
                                 @Field("email") String email,
                                 @Field("deviceId") String deviceId);
}
