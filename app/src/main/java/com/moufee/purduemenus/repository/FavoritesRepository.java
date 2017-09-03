package com.moufee.purduemenus.repository;

import android.arch.lifecycle.LiveData;

import com.moufee.purduemenus.db.FavoriteDao;
import com.moufee.purduemenus.menus.Favorite;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by Ben on 13/08/2017.
 */

public class FavoritesRepository {
    private final FavoriteDao favoriteDao;

    @Inject
    public FavoritesRepository(FavoriteDao favoriteDao){
        this.favoriteDao = favoriteDao;
    }

    public LiveData<List<Favorite>> getFavorites(){
        return favoriteDao.getAll();
    }
}
