package com.example.cavaleralexandru;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities={Job.class},version = 1,exportSchema = false)
public abstract class JoburiDB extends RoomDatabase {
    private static JoburiDB instanta;
    public static JoburiDB getInstanta(Context context){
        if(instanta==null)
            instanta= Room.databaseBuilder(context,JoburiDB.class,"joburi.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        return instanta;
    }
    public abstract JoburiDAO getJoburiDAO();

}
