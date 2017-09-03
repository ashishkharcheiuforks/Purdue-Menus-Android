package com.moufee.purduemenus.menus;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Keep;

/**
 * Created by Ben on 13/08/2017.
 * Represents one favorite, as returned by the dining API
 */
@Keep
@Entity(tableName = "favorites")
public class Favorite {
    public String itemName;
    public String favoriteId;
    @PrimaryKey
    public String itemId;
    public Boolean isVegetarian;
}
