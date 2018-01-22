package eugene.com.transferwise.ui.common;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import eugene.com.livelibrary.Status;
import eugene.com.transferwise.R;
import eugene.com.transferwise.ui.place.PlaceCallbacks;
import eugene.com.transferwise.ui.place.details.PlaceDetailsFragment;
import eugene.com.transferwise.ui.place.list.PlaceListFragment;
import eugene.com.transferwise.util.Constants;


public abstract class BaseFragment extends Fragment {
    public abstract void refreshData();

    public abstract void loading(boolean isLoading);

    public PlaceCallbacks mListener;
    private Snackbar errorBar;

    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null && Constants.IS_v21) {
            if (this instanceof PlaceListFragment) {
                mListener.initStatusBarColor(R.color.colorPrimaryDark);
            }
            if (this instanceof PlaceDetailsFragment) {
                mListener.initStatusBarColor(R.color.colorPrimaryTransparent);
            }
        }
    }

    /**
     * Implement Callbacks
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlaceCallbacks) {
            mListener = (PlaceCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentCallbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Error Handling
     * <p>
     * TODO Don't like this impl
     */
    public void showError(Status status, final String message) {
        loading(status == Status.LOADING);
        switch (status) {
            case SUCCESS:
                if (errorBar != null) {
                    errorBar.dismiss();
                }
                break;
            case LOADING:
                break;
            case ERROR:
                if (getErrorBar(message) != null) {
                    errorBar.show();
                }
                break;
        }
    }

    private Snackbar getErrorBar(String message) {
        if (errorBar == null && getView() != null) {
            errorBar = Snackbar.make(getView(), message, Snackbar.LENGTH_INDEFINITE);
            errorBar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    errorBar = null;
                    refreshData();
                }
            });
            return errorBar;
        } else if (!((TextView) errorBar.getView().findViewById(android.support.design.R.id.snackbar_text)).getText().toString().equals(message)) {
            return null;
        } else {
            return null;
        }
    }
}
