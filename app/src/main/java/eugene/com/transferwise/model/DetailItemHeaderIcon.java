package eugene.com.transferwise.model;

import android.arch.persistence.room.Ignore;

import eugene.com.transferwise.ui.place.details.DetailsRecyclerAdapter;

public class DetailItemHeaderIcon extends DetailItem {
    private String text;
    private Integer drawable;

    public DetailItemHeaderIcon(String text, Integer drawable) {
        this.text = text;
        this.drawable = drawable;
    }

    public String getText() {
        return text;
    }

    public Integer getDrawable() {
        return drawable;
    }

    @Ignore
    @Override
    public int viewType() {
        return DetailsRecyclerAdapter.TYPE_HEADER_ICON;
    }
}
