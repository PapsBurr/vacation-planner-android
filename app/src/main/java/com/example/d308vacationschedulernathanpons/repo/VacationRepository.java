package com.example.d308vacationschedulernathanpons.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.d308vacationschedulernathanpons.model.Excursion;
import com.example.d308vacationschedulernathanpons.model.User;
import com.example.d308vacationschedulernathanpons.model.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VacationRepository {

    private VacationDao mVacationDao;
    private ExcursionDao mExcursionDao;
    private UserDao mUserDao;
    private static VacationRepository mVacationRepository;

    private LiveData<List<Vacation>> mAllVacations;
    private LiveData<List<Excursion>> mAllExcursions;
    private LiveData<List<Excursion>> mAllAssociatedExcursions;
    private int userId;
    private LiveData<List<User>> mAllUsers;

    private LiveData<User> mUser;
    private String username;
    private int vacationId;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /*
    public static VacationRepository getInstance(Context context) {
        if (mVacationRepository == null) {
            mVacationRepository = new VacationRepository(context);
        }
        return mVacationRepository;
    }

     */

    //
    // VACATION CREATE READ UPDATE DELETE METHODS
    //

    public VacationRepository(Application application) {
        VacationDatabase db = VacationDatabase.getDatabase(application);

        mVacationDao = db.vacationDao();
        mExcursionDao = db.excursionDao();
        mUserDao = db.userDao();
        mAllVacations = mVacationDao.getAllVacations();
        mAllExcursions = mExcursionDao.getAllExcursions();
        mAllAssociatedExcursions = mExcursionDao.getAssociatedExcursions(vacationId);
        mAllUsers = mUserDao.getAllUsers();

    }

    public LiveData<List<Vacation>> getmAllVacations() {
        executor.execute(() -> {
            mAllVacations = mVacationDao.getAllVacations();
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mAllVacations;
    }

    /*
    public Vacation getVacation(id) {
        executor.execute(() -> {
            mVacation = mVacationDao.getVacation();
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mVacation;
    }

     */

    public void insert(Vacation vacation) {
        executor.execute(() -> {
            mVacationDao.insert(vacation);
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Vacation vacation) {
        executor.execute(() -> {
            mVacationDao.update(vacation);
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Vacation vacation) {
        executor.execute(() -> {
            mVacationDao.delete(vacation);
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //
    // EXCURSION CREATE READ UPDATE DELETE METHODS
    //

    public LiveData<List<Excursion>> getmAllExcursions() {
        executor.execute(() -> {
            mAllExcursions = mExcursionDao.getAllExcursions();
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mAllExcursions;
    }

    public LiveData<List<Excursion>> getmAllAssociatedExcursions(int vacationId) {
        executor.execute(() -> {
            mAllAssociatedExcursions = mExcursionDao.getAssociatedExcursions(vacationId);
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mAllAssociatedExcursions;
    }

    public void insert(Excursion excursion) {
        executor.execute(() -> {
            mExcursionDao.insert(excursion);
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Excursion excursion) {
        executor.execute(() -> {
            mExcursionDao.update(excursion);
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Excursion excursion) {
        executor.execute(() -> {
            mExcursionDao.delete(excursion);
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Users

    public int getUserId(String username) {
        executor.execute(() -> {
            userId = mUserDao.getUserId(username);
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public LiveData<List<User>> getmAllUsers() {
        executor.execute(() -> {
            mAllUsers = mUserDao.getAllUsers();
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mAllUsers;
    }

    public LiveData<User> getUserById(int id) {
        executor.execute(() -> {
            mUser = mUserDao.getUserById(id);
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mUser;
    }

    public void insert(User user) {
        executor.execute(() -> {
            mUserDao.insert(user);
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        executor.execute(() -> {
            mUserDao.update(user);
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(User user) {
        executor.execute(() -> {
            mUserDao.delete(user);
        });
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
