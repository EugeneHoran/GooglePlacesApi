package eugene.com.transferwise.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import eugene.com.transferwise.db.converters.PlaceConverters;
import eugene.com.transferwise.db.dao.DetailsDao;
import eugene.com.transferwise.db.dao.PlacesDao;
import eugene.com.transferwise.db.entity.Details;
import eugene.com.transferwise.db.entity.Place;

@Database(
        entities = {
                Place.class,
                Details.class
        },
        version = 1,
        exportSchema = false)
@TypeConverters(PlaceConverters.class)
public abstract class PlacesDatabase extends RoomDatabase {
    @VisibleForTesting
    private static final String DATABASE_NAME = "places-db";
    private static PlacesDatabase instance;

    public abstract PlacesDao getPlacesDao();

    public abstract DetailsDao getDetailsDao();

    public static PlacesDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (PlacesDatabase.class) {
                if (instance == null) {
                    instance = create(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private static PlacesDatabase create(final Context context) {
        return Room.databaseBuilder(context, PlacesDatabase.class, DATABASE_NAME).build();
    }
}
