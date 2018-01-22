package eugene.com.transferwise.db.entity;

import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("photo_reference")
    private String photoReference;

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
}
