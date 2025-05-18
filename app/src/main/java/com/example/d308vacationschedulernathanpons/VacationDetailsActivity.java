package com.example.d308vacationschedulernathanpons;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationschedulernathanpons.model.Excursion;
import com.example.d308vacationschedulernathanpons.model.Vacation;
import com.example.d308vacationschedulernathanpons.viewmodel.ExcursionListViewModel;
import com.example.d308vacationschedulernathanpons.viewmodel.VacationListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetailsActivity extends AppCompatActivity {

    String vacationTitle;
    String vacationHotel;
    String vacationStartDate;
    String vacationEndDate;
    private static int vacationId;
    private EditText editVacationTitle;
    private EditText editVacationHotel;
    private TextView editVacationStartDate;
    private TextView editVacationEndDate;

    private ExcursionListViewModel mExcursionlistViewModel;
    private VacationListViewModel mVacationListViewModel;

    private static int numOfExcursions;

    private static int numOfVacationExcursions;
    private static List<Excursion> vacationExcursions;
    private static Vacation currVacation;

    DatePickerDialog.OnDateSetListener vacationStartDatePicker;
    DatePickerDialog.OnDateSetListener vacationEndDatePicker;
    final Calendar mCalenderStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.vacation_details_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editVacationTitle = findViewById(R.id.edit_vacation_title);
        editVacationHotel = findViewById(R.id.edit_hotel_title);
        editVacationStartDate = findViewById(R.id.edit_start_date);
        editVacationEndDate = findViewById(R.id.edit_end_date);

        vacationId = getIntent().getIntExtra("id", -1);
        vacationTitle = getIntent().getStringExtra("vacation_title");
        vacationHotel = getIntent().getStringExtra("hotel_name");
        vacationStartDate = getIntent().getStringExtra("start_date");
        vacationEndDate = getIntent().getStringExtra("end_date");

        editVacationTitle.setText(vacationTitle);
        editVacationHotel.setText(vacationHotel);
        editVacationStartDate.setText(vacationStartDate);
        editVacationEndDate.setText(vacationEndDate);

        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        editVacationStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateInfo = editVacationStartDate.getText().toString();
                if (dateInfo.isEmpty()) {
                    dateInfo = "05/01/25";
                }
                try {
                    mCalenderStart.setTime(sdf.parse(dateInfo));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetailsActivity.this, vacationStartDatePicker, mCalenderStart.get(Calendar.YEAR), mCalenderStart.get(Calendar.MONTH), mCalenderStart.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        editVacationEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateInfo = editVacationEndDate.getText().toString();
                if (dateInfo.isEmpty()) {
                    dateInfo = "05/01/25";
                }
                try {
                    mCalenderStart.setTime(sdf.parse(dateInfo));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetailsActivity.this, vacationEndDatePicker, mCalenderStart.get(Calendar.YEAR), mCalenderStart.get(Calendar.MONTH), mCalenderStart.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        //Pick dates

        vacationStartDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalenderStart.set(Calendar.YEAR, year);
                mCalenderStart.set(Calendar.MONTH, month);
                mCalenderStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStartDateStart();
            }
        };

        vacationEndDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalenderStart.set(Calendar.YEAR, year);
                mCalenderStart.set(Calendar.MONTH, month);
                mCalenderStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEndDateStart();
            }
        };

        FloatingActionButton addFab = findViewById(R.id.add_excursion_floating_point_button);

        addFab.setOnClickListener(v -> {
            Intent intent = new Intent(VacationDetailsActivity.this, ExcursionDetailsActivity.class);
            intent.putExtra("vacation_id", vacationId);
            startActivity(intent);
        });

        Button saveButton = findViewById(R.id.save_vacation);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean safeDates = compareDates();
                if (safeDates) {
                    Intent intent = new Intent();
                    String vacationTitle = editVacationTitle.getText().toString();
                    String vacationHotel = editVacationHotel.getText().toString();
                    String vacationStartDate = editVacationStartDate.getText().toString();
                    String vacationEndDate = editVacationEndDate.getText().toString();
                    intent.putExtra("vacation_title", vacationTitle);
                    intent.putExtra("hotel_name", vacationHotel);
                    intent.putExtra("start_date", vacationStartDate);
                    intent.putExtra("end_date", vacationEndDate);
                    int id = getIntent().getIntExtra("id", 0);
                    Vacation vacation = new Vacation(id, vacationTitle, vacationHotel, vacationStartDate, vacationEndDate);
                    mVacationListViewModel.insert(vacation);

                    setResult(RESULT_OK, intent);
                    Toast.makeText(VacationDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        mVacationListViewModel = new ViewModelProvider(this).get(VacationListViewModel.class);
        mExcursionlistViewModel = new ViewModelProvider(this).get(ExcursionListViewModel.class);

        Button deleteButton = findViewById(R.id.delete_vacation);
        numOfVacationExcursions = 0;
        mExcursionlistViewModel.getmAllExcursions().observe(this, excursions -> {
            for (Excursion excursion : excursions) {
                if (excursion.getVacationId() == getIntent().getIntExtra("id", -1)) {
                    numOfVacationExcursions++;
                }
            }
        });
        mVacationListViewModel.getmAllVacations().observe(this, vacations -> {
            for (Vacation vacation : vacations) {
                if (vacation.getId() == vacationId) {
                    currVacation = vacation;
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numOfVacationExcursions == 0) {
                    mVacationListViewModel.delete(currVacation);
                    Toast.makeText(VacationDetailsActivity.this,currVacation.getVacationTitle() + " was deleted.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {

                    Toast.makeText(VacationDetailsActivity.this,currVacation.getVacationTitle() + " has existing excursions and cannot be deleted.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        RecyclerView mRecyclerView = findViewById(R.id.excursion_recycler_view);
        final ExcursionAdapter mExcursionAdapter = new ExcursionAdapter(this);
        mRecyclerView.setAdapter(mExcursionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        vacationExcursions = new ArrayList<>();
        mExcursionlistViewModel.getmAllExcursions().observe(this, mAllExcursions -> {
            List<Excursion> matchingExcursions = new ArrayList<>();
            Log.d("mAllExcursions = ", mAllExcursions.toString());
            for (Excursion excursion : mAllExcursions) {
                if (excursion.getVacationId() == vacationId) {
                    vacationExcursions.add(excursion);
                    matchingExcursions.add(excursion);
                }
                Log.d("numOfExcursions = ", Integer.toString(numOfExcursions));
            }
            mExcursionAdapter.setmExcursions(matchingExcursions);
            numOfExcursions = matchingExcursions.size();
        });
    }

    private void updateLabelStartDateStart() {

        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        editVacationStartDate.setText(sdf.format(mCalenderStart.getTime()));
    }

    private void updateLabelEndDateStart() {

        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        editVacationEndDate.setText(sdf.format(mCalenderStart.getTime()));
    }

    private boolean compareDates() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        try {
            Date startDate = sdf.parse(editVacationStartDate.getText().toString());
            Date endDate = sdf.parse(editVacationEndDate.getText().toString());
            if (endDate.before(startDate)) {
                Toast.makeText(VacationDetailsActivity.this, "Vacation end date must be after the starting date", Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vacation_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.share) {
            String intentText;
            intentText = "Vacation info: " + currVacation.getVacationTitle() + " starts on " + currVacation.getStartDate() + " and ends on " + currVacation.getEndDate() + " at the " + currVacation.getHotelName() + " hotel. \n";
            if (!vacationExcursions.isEmpty()) {
                intentText += "Your excursions are: ";
                for (Excursion excursion : vacationExcursions) {
                    intentText += excursion.getExcursionName() + ": on " + excursion.getExcursionDate() + " \n";
                }
            }
            Log.d("intentText= ", intentText);

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, intentText);
            sendIntent.putExtra(Intent.EXTRA_TITLE, currVacation.getVacationTitle());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (item.getItemId() == R.id.notify) {

            String vacationNotifyStartDateString = editVacationStartDate.getText().toString();
            String vacationNotifyEndDateString = editVacationStartDate.getText().toString();
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date excursionNotifyStartDate = null;
            Date excursionNotifyEndDate = null;
            try {
                excursionNotifyStartDate = sdf.parse(vacationNotifyStartDateString);
                excursionNotifyEndDate = sdf.parse(vacationNotifyEndDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long triggerStartDate = excursionNotifyStartDate.getTime();
            Long triggerEndDate = excursionNotifyEndDate.getTime();

            Intent notifyIntentStartDate = new Intent(VacationDetailsActivity.this, MyReceiver.class);
            notifyIntentStartDate.putExtra("key", "Your vacation " + currVacation.getVacationTitle() + " is starting!");
            PendingIntent intentSenderStartDate = PendingIntent.getBroadcast(VacationDetailsActivity.this, ++VacationListActivity.numAlert, notifyIntentStartDate, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerStartDate, intentSenderStartDate);

            Intent notifyIntentEndDate = new Intent(VacationDetailsActivity.this, MyReceiver.class);
            notifyIntentEndDate.putExtra("key", "Your vacation " + currVacation.getVacationTitle() + " ends today!");
            PendingIntent intentSenderEndDate = PendingIntent.getBroadcast(VacationDetailsActivity.this, ++VacationListActivity.numAlert, notifyIntentEndDate, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager2.set(AlarmManager.RTC_WAKEUP, triggerEndDate, intentSenderEndDate);

            Toast.makeText(VacationDetailsActivity.this, "Notification set!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("vacation_title", vacationTitle);
        outState.putString("hotel_name", vacationHotel);
        outState.putString("start_date", vacationStartDate);
        outState.putString("end_date", vacationEndDate);
    }
}