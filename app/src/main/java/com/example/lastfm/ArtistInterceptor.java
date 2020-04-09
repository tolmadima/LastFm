package com.example.lastfm;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ArtistInterceptor implements Interceptor {
    public static final String REQUEST_TYPE = "json";
    public static final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request request = chain.request();
        HttpUrl url = request.url()
                .newBuilder()
                .addQueryParameter("api_key", APP_ID)
                .addQueryParameter("format", REQUEST_TYPE)
                .build();

        request = request
                .newBuilder()
                .url(url)
                .build();

        Response response = chain.proceed(request);
        return response;
    }
}

