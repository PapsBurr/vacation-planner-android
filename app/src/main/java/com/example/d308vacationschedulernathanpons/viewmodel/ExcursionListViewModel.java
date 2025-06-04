package com.example.d308vacationschedulernathanpons.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.d308vacationschedulernathanpons.model.Excursion;
import com.example.d308vacationschedulernathanpons.repo.VacationRepository;

import java.util.List;

public class ExcursionListViewModel extends AndroidViewModel {
    private VacationRepository mVacationRepository;
    private LiveData<List<Excursion>> mAllExursions;

    public ExcursionListViewModel(@NonNull Application application) {
        super(application);
        mVacationRepository = new VacationRepository(application);
        mAllExursions = mVacationRepository.getmAllExcursions();
    }

    public LiveData<List<Excursion>> getmAllExcursions() {
        return mAllExursions;
    }

    public void insert(Excursion excursion) {
        mVacationRepository.insert(excursion);
    }

    public void update(Excursion excursion) {
        mVacationRepository.update(excursion);
    }

    public void delete(Excursion excursion) {
        mVacationRepository.delete(excursion);
    }
}