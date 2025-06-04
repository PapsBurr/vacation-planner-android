package com.example.d308vacationschedulernathanpons.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.d308vacationschedulernathanpons.model.User;
import com.example.d308vacationschedulernathanpons.repo.VacationRepository;

import java.util.List;

public class LogInViewModel extends AndroidViewModel {

    private VacationRepository mVacationRepository;
    private LiveData<List<User>> mAllUsers;
    private String username;

    public LogInViewModel(@NonNull Application application) {
        super(application);
        mVacationRepository = new VacationRepository(application);
        mAllUsers = mVacationRepository.getmAllUsers();
    }


    public LiveData<List<User>> getmAllUsers() { return mAllUsers; }

    public void insert(User user) { mVacationRepository.insert(user); }

    public void update(User user) { mVacationRepository.update(user); }

    public void delete(User user) { mVacationRepository.delete(user); }
}
