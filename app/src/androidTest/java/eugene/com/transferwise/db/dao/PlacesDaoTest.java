package eugene.com.transferwise.db.dao;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import eugene.com.transferwise.db.PlacesDatabaseTest;
import eugene.com.transferwise.db.entity.Place;
import eugene.com.transferwise.util.DataUtil;

import static eugene.com.transferwise.util.LiveDataTestUtil.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class PlacesDaoTest extends PlacesDatabaseTest {
    @Test
    public void testInsertAndRead() throws InterruptedException {
        List<Place> placesList = DataUtil.createPlaces(5);
        placesDatabase.getPlacesDao().insert(placesList);
        List<Place> placesListLoaded = getValue(placesDatabase.getPlacesDao().getPlacesLive());
        assertThat(placesListLoaded, notNullValue());
        assertThat(placesListLoaded.size(), is(5));

        Place place1 = placesList.get(0);
        assertThat(place1.getName(), is("name 0"));
        assertThat(place1.getPhotos(), nullValue());

        Place place2 = placesList.get(1);
        assertThat(place2.getName(), is("name 1"));
    }

    @Test
    public void testReplaceOnConflict() throws InterruptedException {
        Place placeInsert = DataUtil.createPlace("123");
        placesDatabase.getPlacesDao().insertSingle(placeInsert);
        Place returned = getValue(placesDatabase.getPlacesDao().getPlacesByIdLive("123"));
        assertThat(returned, notNullValue());
        placesDatabase.getPlacesDao().insertSingle(returned);
        List<Place> placesListLoaded = getValue(placesDatabase.getPlacesDao().getPlacesLive());
        // return on after replace
        assertThat(placesListLoaded.size(), is(1));
    }
}