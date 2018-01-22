package eugene.com.transferwise.db.entity;


import android.arch.persistence.room.Ignore;

import com.google.gson.annotations.SerializedName;

import eugene.com.transferwise.model.DetailItem;
import eugene.com.transferwise.ui.place.details.DetailsRecyclerAdapter;

public class DetailsReview extends DetailItem {

    @SerializedName("author_name")
    private String authorName;
    @SerializedName("profile_photo_url")
    private String profilePhotoUrl;
    @SerializedName("rating")
    private Integer rating;
    @SerializedName("relative_time_description")
    private String relativeTimeDescription;
    @SerializedName("text")
    private String text;

    public DetailsReview() {
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getRelativeTimeDescription() {
        return relativeTimeDescription;
    }

    public void setRelativeTimeDescription(String relativeTimeDescription) {
        this.relativeTimeDescription = relativeTimeDescription;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Ignore
    @Override
    public int viewType() {
        return DetailsRecyclerAdapter.TYPE_REVIEW;
    }
}
