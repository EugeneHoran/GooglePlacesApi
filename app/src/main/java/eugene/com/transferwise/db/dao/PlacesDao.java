package eugene.com.transferwise.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import eugene.com.transferwise.db.entity.Place;

@Dao
public abstract class PlacesDao {
    @Query("SELECT * FROM places")
    public abstract LiveData<List<Place>> getPlacesLive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<Place> results);

    /**
     * Testing
     */
    @Query("SELECT * FROM places WHERE place_id == :placeId")
    public abstract LiveData<Place> getPlacesByIdLive(String placeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertSingle(Place place);

    @Delete
    public abstract void delete(Place place);
}
