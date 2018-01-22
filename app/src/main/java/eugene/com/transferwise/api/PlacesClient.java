package eugene.com.transferwise.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import eugene.com.livelibrary.LiveDataCallAdapterFactory;
import eugene.com.transferwise.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesClient {
    private static PlacesClient instance = null;

    public static PlacesClient getInstance() {
        if (instance == null) {
            instance = new PlacesClient();
        }
        return instance;
    }

    public PlacesService create(CLIENT client) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BuildConfig.PLACES_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(new LiveDataCallAdapterFactory());
        builder.client(http3Client(client));
        builder.build();
        return builder.build().create(PlacesService.class);
    }

    private OkHttpClient http3Client(final CLIENT client) {
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
                    case DETAILS:
                        break;
                }
                Request.Builder requestBuilder = original.newBuilder().url(urlBuilder.build());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor());
        }
        return builder.build();
    }

    /**
     * Log responses
     * <p>
     * logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
     * logging.setLevel(HttpLoggingInterceptor.Level.BODY);
     * logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
     *
     * @return Logging Interceptor
     */
    private HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return logging;
    }
}
