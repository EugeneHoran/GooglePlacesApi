package eugene.com.transferwise.model;

import android.arch.persistence.room.Ignore;

import eugene.com.transferwise.ui.place.details.DetailsRecyclerAdapter;
import eugene.com.transferwise.util.Constants;


public class DetailItemDivider extends DetailItem {

    private boolean showLine = false;
    private boolean showSpace = false;

    public DetailItemDivider(int which) {
        switch (which) {
            case Constants.DIVIDER_LINE:
                showLine = true;
                break;
            case Constants.DIVIDER_SPACE:
                showSpace = true;
                break;
        }
    }

    public boolean showLine() {
        return showLine;
    }

    public boolean showSpace() {
        return showSpace;
    }

    @Ignore
    @Override
    public int viewType() {
        return DetailsRecyclerAdapter.TYPE_DIVIDER;
    }
}
