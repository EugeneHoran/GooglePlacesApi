package eugene.com.transferwise.db;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;

abstract public class PlacesDatabaseTest {
    protected PlacesDatabase placesDatabase;

    @Before
    public void initDb() {
        placesDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), PlacesDatabase.class).build();
    }

    @After
    public void closeDb() {
        placesDatabase.close();
    }
}