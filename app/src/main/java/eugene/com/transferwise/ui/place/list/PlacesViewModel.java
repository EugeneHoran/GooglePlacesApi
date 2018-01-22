package eugene.com.transferwise.ui.place.list;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import java.util.List;

import eugene.com.livelibrary.Resource;
import eugene.com.transferwise.db.entity.Place;
import eugene.com.transferwise.repository.PlacesRepository;

public class PlacesViewModel extends ViewModel {
    private PlacesRepository placesRepository;
    @VisibleForTesting
    private final MutableLiveData<Boolean> refresh;
    private LiveData<Resource<List<Place>>> placeListRepository = new MutableLiveData<>();

    public PlacesViewModel(final PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
        refresh = new MutableLiveData<>();
        placeListRepository = Transformations.switchMap(refresh, new Function<Boolean, LiveData<Resource<List<Place>>>>() {
            @Override
            public LiveData<Resource<List<Place>>> apply(Boolean input) {
                return placesRepository.loadLocalRestaurants();
            }
        });
    }

    @VisibleForTesting
    LiveData<Resource<List<Place>>> getPlaceRepository() {
        return placeListRepository;
    }

    @VisibleForTesting
    void refresh() {
        placesRepository.resetPlaceListKey();
        refresh.setValue(true);
    }
}
