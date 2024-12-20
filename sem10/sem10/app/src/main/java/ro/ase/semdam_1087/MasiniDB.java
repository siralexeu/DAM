package ro.ase.semdam_1087;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities={Masina.class},version = 1,exportSchema = false)
@TypeConverters({DateConvertor.class})
public abstract class MasiniDB extends RoomDatabase {
    public static final String DB_NAME = "masini.db";
    private static MasiniDB instanta;

    public static MasiniDB getInstanta(Context context) {
        if (instanta == null)
            instanta = Room.databaseBuilder(context,
                            MasiniDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        return instanta;
    }

    public abstract MasiniDAO getMasiniDAO();

}
