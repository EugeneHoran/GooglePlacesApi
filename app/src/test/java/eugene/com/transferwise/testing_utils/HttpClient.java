package eugene.com.transferwise.testing_utils;


import android.support.annotation.NonNull;

import java.io.IOException;

import eugene.com.transferwise.BuildConfig;
import eugene.com.transferwise.api.CLIENT;
import eugene.com.transferwise.api.PlacesClient;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClient {

    public OkHttpClient httpClient(final CLIENT client) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl.Builder urlBuilder = originalHttpUrl.newBuilder();
                urlBuilder.addPathSegment("json");
                urlBuilder.addQueryParameter("key", BuildConfig.PLACES_KEY);
                switch (client) {
                    case PLACE:
                        urlBuilder.addQueryParameter("radius", "5000");
                        urlBuilder.addQueryParameter("type", "restaurant");
                        urlBuilder.addQueryParameter("location", BuildConfig.PLACES_LONDON_LAT_LONG);
                        break;
                }
                Request.Builder requestBuilder = original.newBuilder().url(urlBuilder.build());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return builder.build();
    }
}
