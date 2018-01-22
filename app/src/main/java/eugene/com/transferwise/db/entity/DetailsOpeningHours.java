
package eugene.com.transferwise.db.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailsOpeningHours {

    @SerializedName("open_now")
    private Boolean openNow;
    @SerializedName("weekday_text")
    private List<String> weekdayText = null;

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public List<String> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<String> weekdayText) {
        this.weekdayText = weekdayText;
    }

}
