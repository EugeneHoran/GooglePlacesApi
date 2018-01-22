package eugene.com.transferwise.model;

import android.arch.persistence.room.Ignore;
import android.text.Html;
import android.text.Spanned;

import eugene.com.transferwise.ui.place.details.DetailsRecyclerAdapter;

public class DetailItemTime extends DetailItem {

    private String text;

    public DetailItemTime(String text) {
        this.text = text;
    }

    public Spanned getText() {
        if (text == null) {
            return Html.fromHtml("");
        }
        String[] separated = text.split(":");
        String day = separated[0].trim();
        String dayTimeOrOpen = separated[1].trim();
        if (separated.length == 2) {//#191919
            return Html.fromHtml("<font color=\"#323232\"><b>" + day + "</b></font>  " + dayTimeOrOpen);
        } else if (separated.length == 4) {
            return Html.fromHtml("<font color=\"#323232\"><b>" + day + "</b></font>  " + dayTimeOrOpen + ":" + separated[2] + ":" + separated[3]);
        }
        return Html.fromHtml("");
    }

    @Ignore
    @Override
    public int viewType() {
        return DetailsRecyclerAdapter.TYPE_TIME;
    }
}
