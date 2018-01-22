package eugene.com.transferwise.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import eugene.com.transferwise.db.entity.Place;

public class PlacesResult {
    @SerializedName("next_page_token")
    private String nextPageToken;
    @SerializedName("results")
    private List<Place> placeList = null;
    @SerializedName("status")
    private String status;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
