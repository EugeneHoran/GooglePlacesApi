package eugene.com.transferwise.ui.place.details;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import eugene.com.livelibrary.Resource;
import eugene.com.transferwise.db.entity.Details;
import eugene.com.transferwise.model.PlaceDetailsData;
import eugene.com.transferwise.repository.DetailsRepository;
import eugene.com.transferwise.util.DetailsFilter;

public class PlaceDetailsViewModel extends ViewModel {
    private DetailsRepository repository;
    private MediatorLiveData<PlaceDetailsData> detailsData = new MediatorLiveData<>();

    @VisibleForTesting
    final MutableLiveData<String> placeId;

    public PlaceDetailsViewModel(final DetailsRepository repository) {
        this.repository = repository;
        placeId = new MutableLiveData<>();
        LiveData<Resource<Details>> placeListRepository = Transformations.switchMap(placeId, new Function<String, LiveData<Resource<Details>>>() {
            @Override
            public LiveData<Resource<Details>> apply(String placeId) {
                if (placeId != null) {
                    return repository.loadPlaceDetails(placeId);
                }
                return null;
            }
        });
        detailsData.addSource(placeListRepository, new Observer<Resource<Details>>() {
            @Override
            public void onChanged(@Nullable Resource<Details> detailsResource) {
                if (detailsResource != null) {
                    details().setValue(new PlaceDetailsData(detailsResource,
                            new DetailsFilter().getDetailsFilteredList(detailsResource.data)));
                }
            }
        });
    }

    @VisibleForTesting
    public MutableLiveData<PlaceDetailsData> details() {
        return detailsData;
    }

    @VisibleForTesting
    void setPlaceId(String id) {
        if (id != null) {
            repository.refresh();
        }
        placeId.setValue(id);
    }
}
