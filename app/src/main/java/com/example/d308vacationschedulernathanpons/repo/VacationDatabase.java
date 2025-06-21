package com.example.d308vacationschedulernathanpons.repo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.d308vacationschedulernathanpons.model.Excursion;
import com.example.d308vacationschedulernathanpons.model.User;
import com.example.d308vacationschedulernathanpons.model.Vacation;

import java.util.ConcurrentModificationException;

@Database(entities = {Vacation.class, Excursion.class, User.class}, version = 19, exportSchema = false)
public abstract class VacationDatabase extends RoomDatabase {

    public abstract VacationDao vacationDao();
    public abstract ExcursionDao excursionDao();
    public abstract UserDao userDao();
    private static volatile VacationDatabase mVacationDatabase;

    static VacationDatabase getDatabase(final Context context) {
        if(mVacationDatabase == null) {
            synchronized (VacationDatabase.class) {
                mVacationDatabase = Room.databaseBuilder(context.getApplicationContext(), VacationDatabase.class, "vacations.db")
                        .fallbackToDestructiveMigration()
                        .build();
            }

        }
        return mVacationDatabase;
    }

}
