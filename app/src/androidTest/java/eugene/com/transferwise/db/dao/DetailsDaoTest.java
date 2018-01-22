package eugene.com.transferwise.db.dao;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import eugene.com.transferwise.db.PlacesDatabaseTest;
import eugene.com.transferwise.db.entity.Details;
import eugene.com.transferwise.db.entity.DetailsReview;
import eugene.com.transferwise.db.entity.Photo;
import eugene.com.transferwise.db.entity.Place;
import eugene.com.transferwise.util.DataUtil;

import static eugene.com.transferwise.util.LiveDataTestUtil.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class DetailsDaoTest extends PlacesDatabaseTest {
    @Test
    public void testInsertAndRead() throws InterruptedException {
        String id = UUID.randomUUID().toString();
        // First insert place for Foreign Key ref
        placesDatabase.getPlacesDao().insertSingle(DataUtil.createPlace(id));
        Place place = getValue(placesDatabase.getPlacesDao().getPlacesByIdLive(id));
        assertThat(place, notNullValue());
        assertThat(place.getPlaceId(), is(id));
        // Insert Details
        placesDatabase.getDetailsDao().insert(DataUtil.createDetails(id));
        Details details = getValue(placesDatabase.getDetailsDao().getDetailsLive(id));
        assertThat(details, notNullValue());

        // Photos
        assertThat(details.getPhotos().size(), is(3));
        Photo photo2 = details.getPhotos().get(1);
        assertThat(photo2.getPhotoReference(), is("photo_ref1"));
        // Reviews
        assertThat(details.getReviews().size(), is(5));
        DetailsReview review = details.getReviews().get(0);
        assertThat(review.getText(), is("text0"));
        assertThat(review.getAuthorName(), is("name0"));
        DetailsReview review1 = details.getReviews().get(2);
        assertThat(review1.getText(), is("text2"));
        assertThat(review1.getAuthorName(), is("name2"));

        assertThat(place.getPlaceId(), is(details.getPlaceId()));
    }

    @Test
    public void testDeletePrimaryKey() throws InterruptedException {
        String id = UUID.randomUUID().toString();
        // First insert place for Foreign Key ref
        placesDatabase.getPlacesDao().insertSingle(DataUtil.createPlace(id));
        Place place = getValue(placesDatabase.getPlacesDao().getPlacesByIdLive(id));
        assertThat(place, notNullValue());
        assertThat(place.getPlaceId(), is(id));
        // Insert Details
        placesDatabase.getDetailsDao().insert(DataUtil.createDetails(id));
        Details details = getValue(placesDatabase.getDetailsDao().getDetailsLive(id));
        assertThat(details.getName(), is("name"));
        assertThat(details, notNullValue());

        assertThat(place.getPlaceId(), is(details.getPlaceId()));
        // Delete Place and Details by primary key
        placesDatabase.getPlacesDao().delete(place);
        assertThat(getValue(placesDatabase.getPlacesDao().getPlacesByIdLive(id)), nullValue());
        assertThat(getValue(placesDatabase.getDetailsDao().getDetailsLive(id)), nullValue());
    }

}