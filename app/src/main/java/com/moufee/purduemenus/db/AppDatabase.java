package com.moufee.purduemenus.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.moufee.purduemenus.menus.Favorite;

/**
 * The Room Database
 */
@Database(entities = {Favorite.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();

}
