package eugene.com.transferwise.ui.place.list;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eugene.com.livelibrary.Resource;
import eugene.com.transferwise.PlacesApplication;
import eugene.com.transferwise.R;
import eugene.com.transferwise.api.CLIENT;
import eugene.com.transferwise.databinding.FragmentPlacesBinding;
import eugene.com.transferwise.db.entity.Place;
import eugene.com.transferwise.repository.PlacesRepository;
import eugene.com.transferwise.ui.common.BaseFragment;
import eugene.com.transferwise.util.AppFactory;

@SuppressLint("VisibleForTests")
public class PlaceListFragment extends BaseFragment implements PlaceListRecyclerAdapter.PlaceClickCallback {
    //    private static final String TAG = PlaceListFragment.class.getName();
    private PlacesViewModel model;
    private FragmentPlacesBinding binding;
    private PlaceListRecyclerAdapter adapter;

    public PlaceListFragment() {
    }

    public static PlaceListFragment newInstance() {
        return new PlaceListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null && getActivity().getApplicationContext() != null) {
            PlacesApplication app = (PlacesApplication) getActivity().getApplicationContext();
            PlacesRepository repository = PlacesRepository.getInstance(
                    app.getAppExecutors(),
                    app.getDatabase().getPlacesDao(),
                    app.getPlacesClient().create(CLIENT.PLACE)
            );
            model = ViewModelProviders.of(this, new AppFactory(repository)).get(PlacesViewModel.class);
        }
        adapter = new PlaceListRecyclerAdapter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = DataBindingUtil.inflate(inflater, R.layout.fragment_places, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setFragment(this);
        binding.recycler.setAdapter(adapter);
        if (savedInstanceState == null) {
            model.refresh();
        }
        observePlaces(model);
    }

    private void observePlaces(final PlacesViewModel model) {
        model.getPlaceRepository().observe(this, new Observer<Resource<List<Place>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Place>> resource) {
                binding.swiperefresh.setRefreshing(false);
                if (resource != null) {
                    showError(resource.status, resource.message);
                    adapter.setItems(resource.data != null ? resource.data : new ArrayList<Place>());
                }
            }
        });
    }

    @Override
    public void loading(boolean isLoading) {
        isLoading = isLoading && adapter.getItemCount() == 0;
        binding.progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        binding.swiperefresh.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void refreshData() {
        model.refresh();
    }

    @Override
    public void onPlaceClick(Place place) {
        if (mListener != null) {
            boolean hasPhotos = place.getPhotos() != null && place.getPhotos().size() > 0;
            mListener.onPlaceClick(binding.appBar, place.getPlaceId(), hasPhotos);
        }
    }
}
