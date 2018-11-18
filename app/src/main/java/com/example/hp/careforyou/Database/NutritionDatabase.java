package com.example.hp.careforyou.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.example.hp.careforyou.DietPlane.DietConsumedItemsPojo;
import com.example.hp.careforyou.DietPlane.DietItemsDao;
import com.example.hp.careforyou.DietPlane.DietItemsPojo;

@Database(entities = {NutritionEntity.class, DietItemsPojo.class , DietConsumedItemsPojo.class, Extra1.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class NutritionDatabase extends RoomDatabase {

    private static final String LOG_TAG = NutritionDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "nutritionlist";
    private static NutritionDatabase sInstance;


    public static NutritionDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        NutritionDatabase.class, NutritionDatabase.DATABASE_NAME)
                         .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }


    public abstract NutritionDao nutritionDao();

    public  abstract DietItemsDao dietItemsDao();

    public abstract DietItemsList dietItemsList();


}
