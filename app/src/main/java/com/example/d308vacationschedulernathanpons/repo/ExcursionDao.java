package com.example.d308vacationschedulernathanpons.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308vacationschedulernathanpons.model.Excursion;

import java.util.List;

@Dao
public interface ExcursionDao {

    @Query("Select * From excursions Order by id ASC")
    LiveData<List<Excursion>> getAllExcursions();

    @Query("Select * From excursions Where vacation_id = :vacationId")
    LiveData<List<Excursion>> getAssociatedExcursions(int vacationId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

}
