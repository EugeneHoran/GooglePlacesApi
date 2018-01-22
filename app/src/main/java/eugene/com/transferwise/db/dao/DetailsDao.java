package eugene.com.transferwise.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import eugene.com.transferwise.db.entity.Details;

@Dao
public abstract class DetailsDao {
    @Query("SELECT * FROM details WHERE place_id == :placeId")
    public abstract LiveData<Details> getDetailsLive(String placeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Details details);
}
