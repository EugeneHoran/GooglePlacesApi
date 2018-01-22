package eugene.com.transferwise.repository;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import eugene.com.livelibrary.ApiResponse;
import eugene.com.livelibrary.AppExecutors;
import eugene.com.livelibrary.NetworkBoundResource;
import eugene.com.livelibrary.RateLimiter;
import eugene.com.livelibrary.Resource;
import eugene.com.transferwise.api.PlacesService;
import eugene.com.transferwise.db.PlacesDatabase;
import eugene.com.transferwise.db.entity.Details;
import eugene.com.transferwise.model.DetailsResult;

public class DetailsRepository {
    private static final String KEY_RETAIN = "1";
    private static DetailsRepository instance;

    private RateLimiter<String> repoDetailsRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);
    private PlacesDatabase placeDatabase;
    private PlacesService placesService;
    private AppExecutors appExecutors;

    public static DetailsRepository getInstance(AppExecutors appExecutors, PlacesDatabase placeDatabase, PlacesService placesService) {
        if (instance == null) {
            instance = new DetailsRepository(appExecutors, placeDatabase, placesService);
        }
        return instance;
    }

    private DetailsRepository(final AppExecutors appExecutors, final PlacesDatabase placeDatabase, final PlacesService placesService) {
        this.placeDatabase = placeDatabase;
        this.placesService = placesService;
        this.appExecutors = appExecutors;
    }

    public void refresh() {
        repoDetailsRateLimit.reset(KEY_RETAIN);
    }

    public LiveData<Resource<Details>> loadPlaceDetails(final String placeId) {
        return new NetworkBoundResource<Details, DetailsResult>(appExecutors) {
            // DB
            @Override
            protected void saveCallResult(@NonNull DetailsResult item) {
                placeDatabase.getDetailsDao().insert(item.getResult());
            }

            @NonNull
            @Override
            protected LiveData<Details> loadFromDb() {
                return placeDatabase.getDetailsDao().getDetailsLive(placeId);
            }

            // API
            @Override
            protected boolean shouldFetch(@Nullable Details data) {
                return data == null || repoDetailsRateLimit.shouldFetch(KEY_RETAIN);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<DetailsResult>> createCall() {
                return placesService.getDetails(placeId);
            }

            @Override
            protected void onFetchFailed() {
                repoDetailsRateLimit.reset(KEY_RETAIN);
            }

            @Override
            protected DetailsResult processResponse(ApiResponse<DetailsResult> response) {
                return super.processResponse(response);
            }
        }.asLiveData();
    }
}
