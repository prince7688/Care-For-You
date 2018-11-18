package com.example.hp.careforyou.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DietItemsList {

    @Query("SELECT * FROM dietplaneitemlist")
    List<Extra1> loadAllItems();

    @Insert
    void insertItem(Extra1 extra);
}
