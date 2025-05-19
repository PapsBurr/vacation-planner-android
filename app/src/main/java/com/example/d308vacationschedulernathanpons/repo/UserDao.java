package com.example.d308vacationschedulernathanpons.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308vacationschedulernathanpons.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("Select id From users Where username = :username")
    int getUserId(String username);

    @Query("Select * From users Order by id ASC")
    LiveData<List<User>> getAllUsers();

    @Query("Select * From users where id = :id")
    LiveData<User> getUserById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);


}
