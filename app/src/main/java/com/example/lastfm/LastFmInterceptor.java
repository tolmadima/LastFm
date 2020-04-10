package com.example.lastfm;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LastFmInterceptor implements Interceptor {
    private final String REQUEST_TYPE = "json";
    private final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";
    private final String APP_ID_QUERY_PARAMETER = "api_key";
    private final String REQUEST_TYPE_QUERY_PARAMETER = "format";
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request request = chain.request();
        HttpUrl url = request.url()
                .newBuilder()
                .addQueryParameter(APP_ID_QUERY_PARAMETER, APP_ID)
                .addQueryParameter(REQUEST_TYPE_QUERY_PARAMETER, REQUEST_TYPE)
                .build();

        request = request
                .newBuilder()
                .url(url)
                .build();

        Response response = chain.proceed(request);
        return response;
    }
}

