package eugene.com.transferwise.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "places")
public class Place {

    @Override
    public String toString() {
        return "Place{" +
                "placeId='" + placeId + '\'' +
                ", name='" + name + '\'' +
                ", priceLevel=" + priceLevel +
                ", rating=" + rating +
                ", vicinity='" + vicinity + '\'' +
                '}';
    }

    @NonNull
    @PrimaryKey
    @SerializedName("place_id")
    @ColumnInfo(name = "place_id")
    private String placeId;

    @SerializedName("name")
    private String name;

    @SerializedName("photos")
    private List<Photo> photos;

    // 0 - 4 (Free - Very Expensive)
    @SerializedName("price_level")
    private Integer priceLevel;

    // Rating from 1.0 to 5.0
    @SerializedName("rating")
    private Float rating;

    // Readable location
    @SerializedName("vicinity")
    private String vicinity;

    @NonNull
    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(@NonNull String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
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

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    /**
     * Formatted
     */
    public String getDisplayPhotoUrl() {
        if (photos != null && photos.size() > 0) {
            return photos.get(0).getPhotoReference();
        }
        return null;
    }
}
