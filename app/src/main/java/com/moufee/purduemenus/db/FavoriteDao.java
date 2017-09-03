package com.moufee.purduemenus.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.moufee.purduemenus.menus.Favorite;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Ben on 8/29/17.
 */
@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    LiveData<List<Favorite>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Favorite...favorites);

    @Delete
    void delete(Favorite favorite);
}
