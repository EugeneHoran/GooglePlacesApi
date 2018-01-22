package eugene.com.transferwise.ui.place.details;

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

import eugene.com.livelibrary.Resource;
import eugene.com.transferwise.PlacesApplication;
import eugene.com.transferwise.R;
import eugene.com.transferwise.api.CLIENT;
import eugene.com.transferwise.databinding.FragmentPlaceDetailsBinding;
import eugene.com.transferwise.db.entity.Details;
import eugene.com.transferwise.model.PlaceDetailsData;
import eugene.com.transferwise.repository.DetailsRepository;
import eugene.com.transferwise.ui.common.BaseFragment;
import eugene.com.transferwise.util.AppFactory;
import eugene.com.transferwise.util.view.ZoomOutPageTransformer;

@SuppressLint("VisibleForTests")
public class PlaceDetailsFragment extends BaseFragment implements View.OnClickListener {
    //    private static final String TAG = PlaceDetailsFragment.class.getName();
    private static final String ARG_PLACE_ID = "args_place_id";
    private static final String ARG_HAS_PHOTO = "args_has_photo";

    private String placeId;
    private boolean hasPhotos = false;

    private DetailsRecyclerAdapter adapter;
    private PlaceDetailsPagerAdapter pagerAdapter;
    private PlaceDetailsViewModel model;
    private FragmentPlaceDetailsBinding binding;

    public PlaceDetailsFragment() {
    }

    public static PlaceDetailsFragment newInstance(String placeId, boolean hasPhoto) {
        PlaceDetailsFragment fragment = new PlaceDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PLACE_ID, placeId);
        bundle.putBoolean(ARG_HAS_PHOTO, hasPhoto);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getActivity() != null && getActivity().getApplicationContext() != null) {
            placeId = getArguments().getString(ARG_PLACE_ID);
            hasPhotos = getArguments().getBoolean(ARG_HAS_PHOTO);
            PlacesApplication app = (PlacesApplication) getActivity().getApplicationContext();
            DetailsRepository repository = DetailsRepository.getInstance(
                    app.getAppExecutors(),
                    app.getDatabase(),
                    app.getPlacesClient().create(CLIENT.DETAILS)
            );
            model = ViewModelProviders.of(this, new AppFactory(repository)).get(PlaceDetailsViewModel.class);
        }
        adapter = new DetailsRecyclerAdapter();
        pagerAdapter = new PlaceDetailsPagerAdapter(getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = DataBindingUtil.inflate(inflater, R.layout.fragment_place_details, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.appBar.setHasPhotos(hasPhotos);
        binding.setFragment(this);
        binding.appBar.toolbar.setOnClickListener(this);
        binding.appBar.pager.setPageTransformer(true, new ZoomOutPageTransformer());
        binding.appBar.pager.setAdapter(pagerAdapter);
        binding.recycler.setAdapter(adapter);
        observeDetailsNew(model);
        if (savedInstanceState == null) {
            refreshData();
            adjustTitle();
        }
    }

    @Override
    public void loading(boolean isLoading) {
        binding.progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void refreshData() {
        model.setPlaceId(placeId);
    }

    private void observeDetailsNew(final PlaceDetailsViewModel model) {
        model.details().observe(this, new Observer<PlaceDetailsData>() {
            @Override
            public void onChanged(@Nullable PlaceDetailsData placeDetailsData) {
                if (placeDetailsData != null && placeDetailsData.getDetailsResource() != null) {
                    Resource<Details> detailsResource = placeDetailsData.getDetailsResource();
                    handleDataRefresh(detailsResource);
                    adapter.setItems(placeDetailsData.getDetailsList());
                    if (detailsResource.data != null) {
                        pagerAdapter.setItems(detailsResource.data.getPhotos());
                        binding.appBar.indicator.setupWithViewPager(binding.appBar.pager);
                        binding.appBar.setDetails(detailsResource.data);
                    }
                }
            }
        });
    }

    private void handleDataRefresh(Resource<Details> resource) {
        binding.swiperefresh.setRefreshing(false);
        showError(resource.status, resource.message);
    }

    @Override
    public void onClick(View v) {
        if (getActivity() != null)
            getActivity().getSupportFragmentManager().popBackStack();
    }

    /**
     * Bug in support library.
     * <p>
     * https://issuetracker.google.com/issues/37140811
     */

    private void adjustTitle() {
        binding.appBar.toolbarLayout.post(new Runnable() {
            @Override
            public void run() {
                binding.appBar.toolbarLayout.requestLayout();
            }
        });
    }
}
