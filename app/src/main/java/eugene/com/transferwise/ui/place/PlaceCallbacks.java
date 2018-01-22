package eugene.com.transferwise.ui.place;

import android.view.View;

import eugene.com.transferwise.db.entity.Place;

public interface PlaceCallbacks {
    void initStatusBarColor(Integer color);

    void onPlaceClick(View sharedView, String placeId, boolean hasPhotos);
}
