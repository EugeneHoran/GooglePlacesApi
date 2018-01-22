package eugene.com.transferwise.model;

import android.arch.persistence.room.Ignore;

import eugene.com.transferwise.ui.place.details.DetailsRecyclerAdapter;

public class DetailItemHeader extends DetailItem {

    private String text;

    public DetailItemHeader(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Ignore
    @Override
    public int viewType() {
        return DetailsRecyclerAdapter.TYPE_HEADER;
    }
}
