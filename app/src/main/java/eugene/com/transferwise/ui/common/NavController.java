package eugene.com.transferwise.ui.common;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.view.View;

import eugene.com.transferwise.R;
import eugene.com.transferwise.ui.place.details.PlaceDetailsFragment;
import eugene.com.transferwise.ui.place.list.PlaceListFragment;
import eugene.com.transferwise.util.Constants;
import eugene.com.transferwise.util.view.TransitionHelper;


public class NavController {
    private static final String TAG_FRAG_PLACE_LIST = "tag_frag_place_list";
    private static final String TAG_FRAG_PLACE_DETAILS = "tag_frag_place_details";

    private final int container;
    private final TransitionHelper transitionHelper;
    private final FragmentManager fm;

    public NavController(FragmentManager fm) {
        this.container = R.id.container;
        this.transitionHelper = new TransitionHelper();
        this.fm = fm;
    }

    public void navToPlaceList() {
        PlaceListFragment currentFragment = PlaceListFragment.newInstance();
        transitionHelper.initTransitionN(currentFragment);
        fm.beginTransaction()
                .replace(container, currentFragment, TAG_FRAG_PLACE_LIST)
                .commit();
    }

    public void navToDetails(View sharedView, String placeId, boolean hasPhotos) {
        PlaceDetailsFragment fragment = PlaceDetailsFragment.newInstance(placeId, hasPhotos);
        transitionHelper.initTransitionN(fragment);
        FragmentTransaction transaction = fm.beginTransaction();
        if (Constants.IS_v21) {
            transaction.addSharedElement(sharedView, ViewCompat.getTransitionName(sharedView));
        }
        transaction.replace(R.id.container, fragment, TAG_FRAG_PLACE_DETAILS)
                .addToBackStack(TAG_FRAG_PLACE_DETAILS)
                .commit();
    }
}
