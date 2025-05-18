package com.example.d308vacationschedulernathanpons.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308vacationschedulernathanpons.model.Excursion;
import com.example.d308vacationschedulernathanpons.model.Vacation;

import java.util.List;

@Dao
public interface VacationDao {

    @Query("Select * From vacations Order by id ASC")
    LiveData<List<Vacation>> getAllVacations();

    /*
    @Query("Select * From vacations Where id = :vacationId")
    Vacation getVacation(vacationId);

     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);


}
