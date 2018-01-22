package eugene.com.transferwise.util;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import eugene.com.transferwise.repository.DetailsRepository;
import eugene.com.transferwise.repository.PlacesRepository;
import eugene.com.transferwise.ui.place.details.PlaceDetailsViewModel;
import eugene.com.transferwise.ui.place.list.PlacesViewModel;


public class AppFactory extends ViewModelProvider.NewInstanceFactory {
    private PlacesRepository placesRepository;
    private DetailsRepository detailsRepository;

    public AppFactory(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    public AppFactory(DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlacesViewModel.class)) {
            return (T) new PlacesViewModel(placesRepository);
        } else if (modelClass.isAssignableFrom(PlaceDetailsViewModel.class)) {
            return (T) new PlaceDetailsViewModel(detailsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
