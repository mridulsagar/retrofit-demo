package com.example.mridul.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;

import com.example.mridul.retrofitdemo.models.Post;
import com.example.mridul.retrofitdemo.networkLayer.ApiService;
import com.example.mridul.retrofitdemo.networkLayer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AppCompatTextView tvPost;
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getPostNetworkCall();

    }

    private void init() {
        Log.d(TAG, "init: ");
        tvPost = findViewById(R.id.tvPost);
        Retrofit retrofit = RetrofitClient.getClient(ApiService.BASE_URL);
        apiService = retrofit.create(ApiService.class);
    }

    private void getPostNetworkCall() {
        Log.d(TAG, "getPostNetworkCall: ");
        Call<List<Post>> getPosts = apiService.getPosts();

        getPosts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d(TAG, "onResponse: response code: " + response.code());
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        showDataInView(response.body());
                    else
                        tvPost.setText("No post found.");
                } else {
                    tvPost.setText("Network request un-successful, response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(TAG, "onFailure: StackTrace: " + t.getStackTrace());
                tvPost.setText("Network request failed. \n" + t.getLocalizedMessage());
            }
        });
    }

    private void showDataInView(List<Post> postList) {
        Log.d(TAG, "showDataInView: ");

        StringBuilder stringBuilder = new StringBuilder();

        for (Post post : postList) {
            stringBuilder.append(post.getTitle()).append("\n\n")
                    .append(post.getBody()).append("\n\n\n");
        }

        tvPost.setText(stringBuilder.toString());
    }
}
