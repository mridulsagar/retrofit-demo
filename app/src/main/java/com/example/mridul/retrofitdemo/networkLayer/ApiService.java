package com.example.mridul.retrofitdemo.networkLayer;

import com.example.mridul.retrofitdemo.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    String BASE_URL = "http://jsonplaceholder.typicode.com";

    @GET("/posts")
    Call<List<Post>> getPosts();
}
