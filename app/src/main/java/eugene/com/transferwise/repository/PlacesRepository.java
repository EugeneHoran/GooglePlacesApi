package eugene.com.transferwise.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import eugene.com.livelibrary.ApiResponse;
import eugene.com.livelibrary.AppExecutors;
import eugene.com.livelibrary.NetworkBoundResource;
import eugene.com.livelibrary.RateLimiter;
import eugene.com.livelibrary.Resource;
import eugene.com.transferwise.api.PlacesService;
import eugene.com.transferwise.db.dao.PlacesDao;
import eugene.com.transferwise.db.entity.Place;
import eugene.com.transferwise.model.PlacesResult;

public class PlacesRepository {
    private static final String KEY_RETAIN = "1";
    private static PlacesRepository instance;

    private RateLimiter<String> repoListRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);
    private PlacesDao placesDao;
    private PlacesService placesService;
    private AppExecutors appExecutors;

    public static PlacesRepository getInstance(AppExecutors appExecutors, PlacesDao placesDao, PlacesService placesService) {
        if (instance == null) {
            instance = new PlacesRepository(appExecutors, placesDao, placesService);
        }
        return instance;
    }

    private PlacesRepository(final AppExecutors appExecutors, final PlacesDao placesDao, final PlacesService placesService) {
        this.placesDao = placesDao;
        this.placesService = placesService;
        this.appExecutors = appExecutors;
    }

    public void resetPlaceListKey() {
        repoListRateLimit.reset(KEY_RETAIN);
    }

    public LiveData<Resource<List<Place>>> loadLocalRestaurants() {
        return new NetworkBoundResource<List<Place>, PlacesResult>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull PlacesResult item) {
                placesDao.insert(item.getPlaceList());
            }

            @NonNull
            @Override
            protected LiveData<List<Place>> loadFromDb() {
                return placesDao.getPlacesLive();
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Place> data) {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(KEY_RETAIN);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PlacesResult>> createCall() {
                return placesService.getRestaurants();
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(KEY_RETAIN);
            }

            @Override
            protected PlacesResult processResponse(ApiResponse<PlacesResult> response) {
                return super.processResponse(response);
            }
        }.asLiveData();
    }
}
