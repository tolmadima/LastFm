package com.example.lastfm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String REQUEST_TYPE = "json";
    public static final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";

    ServiceGenerator(){
        lastFMClient = createService(LastFMClient.class);
    }

    private  final String TAG = "Retrofit";

    private  final String BASE_URL = "http://ws.audioscrobbler.com/2.0/";
    private  Type artistType = new TypeToken<List<Artist>>(){}.getType();

    private  Gson gson = new GsonBuilder()
            .registerTypeAdapter(artistType, new ArtistsDeserializer())
            .create();

    private  HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private OkHttpClient httpClient =
            new OkHttpClient.Builder().addInterceptor(logging)
                    .addInterceptor(new LastFmInterceptor()).build();


    private Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());


    private  Retrofit retrofit = builder.build();

    private <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging)){
            retrofit = builder.build();
            builder.client(httpClient);
        }
        return retrofit.create(serviceClass);
    }

    private LastFMClient lastFMClient;
    public LastFMClient getLastFMClient() {

        return lastFMClient;
    }

    public static ServiceGenerator getInstance() {
        if (instance == null) {
            instance = new ServiceGenerator();
        }
        return instance;
    }

    private static ServiceGenerator instance;
}


