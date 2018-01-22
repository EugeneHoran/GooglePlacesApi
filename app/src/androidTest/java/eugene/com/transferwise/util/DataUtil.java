package eugene.com.transferwise.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import eugene.com.transferwise.db.entity.Details;
import eugene.com.transferwise.db.entity.DetailsReview;
import eugene.com.transferwise.db.entity.Photo;
import eugene.com.transferwise.db.entity.Place;

public class DataUtil {

    public static List<Place> createPlaces(int count) {
        List<Place> places = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Place place = new Place();
            place.setPlaceId(UUID.randomUUID().toString());
            place.setName(("name " + i));
            places.add(place);
        }
        return places;
    }

    public static Place createPlace(String id) {
        Place place = new Place();
        place.setPlaceId(id);
        place.setName(("name"));
        return place;
    }

    public static Details createDetails(String id) {
        Details details = new Details();
        details.setId(UUID.randomUUID().toString());
        details.setName("name");
        details.setFormattedAddress("formatted_address");
        details.setPhotos(createPhotoList());
        details.setReviews(createReviewList());
        details.setPlaceId(id);
        details.setFormattedPhoneNumber("formatted_phone");
        details.setRating(4.1f);
        return details;
    }

    private static List<Photo> createPhotoList() {
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Photo photo = new Photo();
            photo.setPhotoReference("photo_ref" + i);
            photos.add(photo);
        }
        return photos;
    }

    private static List<DetailsReview> createReviewList() {
        List<DetailsReview> detailsReviews = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DetailsReview detailsReview = new DetailsReview();
            detailsReview.setAuthorName("name" + i);
            detailsReview.setText("text" + i);
            detailsReviews.add(detailsReview);
        }
        return detailsReviews;
    }
}
