package com.example.rta_assignment6.dblocal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.rta_assignment6.model.RegionInfo;

@Database(entities = {RegionInfo.class},version = 1)

public abstract class AppDatabase extends RoomDatabase {

    public  abstract RegionInfoDao regionInfoDao();

    private  static  AppDatabase INSTANCE;

    public  static  AppDatabase getDbInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"DB_REGION")
                    .allowMainThreadQueries()
                    .build();

        }
        return  INSTANCE;

    }





}
