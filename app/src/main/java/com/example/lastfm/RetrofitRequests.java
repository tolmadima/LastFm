package com.example.lastfm;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RetrofitRequests {
    private static RetrofitRequests mInstance;
    private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key=b4ab3bf82dcb495e182e04cfc1f12b7b";
    private Retrofit mRetrofit;

    private RetrofitRequests() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitRequests getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitRequests();
        }
        return mInstance;
    }
    public interface JSONPlaceHolderApi {
        @GET("/posts/{id}")
        public Call<Post> getPostWithID(@Path("id") int id);
    }
    public JSONPlaceHolderApi getJSONApi(){
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }

}
