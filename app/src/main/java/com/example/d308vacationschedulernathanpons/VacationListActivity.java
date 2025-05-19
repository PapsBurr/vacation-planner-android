package com.example.d308vacationschedulernathanpons;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationschedulernathanpons.model.Excursion;
import com.example.d308vacationschedulernathanpons.model.Vacation;
import com.example.d308vacationschedulernathanpons.viewmodel.ExcursionListViewModel;
import com.example.d308vacationschedulernathanpons.viewmodel.VacationListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VacationListActivity extends AppCompatActivity {

    public static int numAlert;
    public static int userId;

    private VacationListViewModel mVacationListViewModel;
    private ExcursionListViewModel mExcursionlistViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.vacation_list_title);


        FloatingActionButton addFab = findViewById(R.id.add_vacation_floating_point_button);

        addFab.setOnClickListener(v -> {
            Intent intent = new Intent(VacationListActivity.this, VacationDetailsActivity.class);
            startActivity(intent);
        });
        RecyclerView mRecyclerView = findViewById(R.id.vacation_recycler_view);
        final VacationAdapter mVacationAdapter = new VacationAdapter(this);
        mRecyclerView.setAdapter(mVacationAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mVacationListViewModel = new ViewModelProvider(this).get(VacationListViewModel.class);
        mExcursionlistViewModel = new ViewModelProvider(this).get(ExcursionListViewModel.class);

        String username = getIntent().getStringExtra("username");
        if (username != null) {
            userId = mVacationListViewModel.getUserId(username);
        }

        mVacationListViewModel.getmAllVacations().observe(this, mAllVacations -> {
            List<Vacation> matchingVacations = new ArrayList<>();

            for (Vacation vacation : mAllVacations) {
                if (vacation.getUserId() == userId) {
                    matchingVacations.add(vacation);
                }
            }

            mVacationAdapter.setVacations(matchingVacations);
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.vacation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_vacation) {
            Intent intent = new Intent(VacationListActivity.this, VacationDetailsActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.add_sample) {

            Vacation vacation1 = new Vacation(0, "Bermuda Triangle", "Zeppelin Hotel", "12/12/25", "12/12/26", userId);
            mVacationListViewModel.insert(vacation1);
            Vacation vacation2 = new Vacation(0, "Circus (scary)", "Spooooky Hotel", "10/10/25", "10/20/25", userId);
            mVacationListViewModel.insert(vacation2);
            Vacation vacation3 = new Vacation(0, "Shark Tornado", "Five Star Movie Hotel", "12/12/25", "12/12/26", userId);
            mVacationListViewModel.insert(vacation3);

            Excursion excursion1 = new Excursion(0, "SkyDiving!", "12/12/25", 1, userId);
            mExcursionlistViewModel.insert(excursion1);
            Excursion excursion2 = new Excursion(0, "SkyDiving!", "10/10/25", 2, userId);
            mExcursionlistViewModel.insert(excursion2);
            Excursion excursion3 = new Excursion(0, "SkyDiving!", "12/12/25", 3, userId);
            mExcursionlistViewModel.insert(excursion3);

        }
        return false;
    }
}