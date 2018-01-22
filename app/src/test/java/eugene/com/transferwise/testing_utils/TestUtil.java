package eugene.com.transferwise.testing_utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import eugene.com.transferwise.db.entity.Place;

public class TestUtil {

    public static List<Place> createPlaces(int count) {
        List<Place> places = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Place place = new Place();
            place.setPlaceId(UUID.randomUUID().toString());
            place.setName("NAME " + i);
            places.add(place);
        }
        return places;
    }
}
