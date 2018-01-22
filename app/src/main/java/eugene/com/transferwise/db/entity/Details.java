
package eugene.com.transferwise.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "details",
        foreignKeys = {@ForeignKey(
                entity = Place.class,
                parentColumns = "place_id",
                childColumns = "place_id",
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index(
                value = "place_id")})
public class Details {
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    private String id;

    @SerializedName("place_id")
    @ColumnInfo(name = "place_id")
    private String placeId;

    @SerializedName("name")
    private String name;

    @SerializedName("formatted_phone_number")
    private String formattedPhoneNumber;

    @SerializedName("formatted_address")
    private String formattedAddress;

    @SerializedName("price_level")
    private Integer priceLevel;

    @SerializedName("rating")
    private Float rating;

    @SerializedName("photos")
    private List<Photo> photos = null;

    @SerializedName("opening_hours")
    private DetailsOpeningHours openingHours;

    @SerializedName("reviews")
    private List<DetailsReview> reviews = null;

    public Details() {
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Integer getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(Integer priceLevel) {
        this.priceLevel = priceLevel;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public DetailsOpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(DetailsOpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public List<DetailsReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<DetailsReview> reviews) {
        this.reviews = reviews;
    }

    @Ignore
    public String getRatingText() {
        return String.valueOf(rating);
    }

    @Ignore
    public String getPriceFormatted() {
        if (priceLevel == null) {
            return "";
        }
        switch (priceLevel) {
            case 0: //Free
                return "Free";
            case 1://Inexpensive
                return "$";
            case 2://Moderate
                return "$$";
            case 3://Expensive
                return "$$$";
            case 4://Very Expensive
                return "$$$$";
            default:
                return "";
        }
    }
}
