package eugene.com.transferwise.ui.place;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import eugene.com.transferwise.R;
import eugene.com.transferwise.ui.common.NavController;
import eugene.com.transferwise.util.Constants;

public class PlacesActivity extends AppCompatActivity implements PlaceCallbacks {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_places);
        navController = new NavController(getSupportFragmentManager());
        if (savedInstanceState == null) {
            navController.navToPlaceList();
        }
    }

    @Override
    public void onPlaceClick(View sharedView, String placeId, boolean hasPhotos) {
        navController.navToDetails(sharedView, placeId, hasPhotos);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void initStatusBarColor(Integer color) {
        if (Constants.IS_v21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(
                    ContextCompat.getColor(PlacesActivity.this, color != null ?
                            color : R.color.colorPrimaryDark));
        }
    }
}
