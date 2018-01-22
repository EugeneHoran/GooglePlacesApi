package eugene.com.transferwise.api;


import android.arch.lifecycle.LiveData;

import eugene.com.livelibrary.ApiResponse;
import eugene.com.transferwise.model.DetailsResult;
import eugene.com.transferwise.model.PlacesResult;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesService {
    @GET("nearbysearch/")
    LiveData<ApiResponse<PlacesResult>> getRestaurants();

    @GET("details/")
    LiveData<ApiResponse<DetailsResult>> getDetails(@Query("placeid") String placeId);
}
