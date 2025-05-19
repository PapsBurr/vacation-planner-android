package com.example.d308vacationschedulernathanpons;

import static com.example.d308vacationschedulernathanpons.VacationListActivity.userId;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.lifecycle.ViewModelProvider;

import com.example.d308vacationschedulernathanpons.model.Excursion;
import com.example.d308vacationschedulernathanpons.model.Vacation;
import com.example.d308vacationschedulernathanpons.viewmodel.ExcursionListViewModel;
import com.example.d308vacationschedulernathanpons.viewmodel.VacationListViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetailsActivity extends AppCompatActivity {

    private int id;
    String excursionName;
    String excursionDate;
    private static int vacationId;

    private EditText editExcursionName;
    private TextView editExcursionDate;

    private ExcursionListViewModel mExcursionlistViewModel;
    private VacationListViewModel mVacationListViewModel;

    private static Excursion currExcursion;
    private static Vacation currVacation;

    DatePickerDialog.OnDateSetListener excursionDatePicker;
    final Calendar mCalenderStart = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.excursion_details_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editExcursionName = findViewById(R.id.edit_excursion_name);
        editExcursionDate = findViewById(R.id.edit_excursion_date);

        id = getIntent().getIntExtra("id", -1);
        excursionName = getIntent().getStringExtra("excursion_name");
        excursionDate = getIntent().getStringExtra("excursion_date");
        vacationId = getIntent().getIntExtra("vacation_id", -1);

        editExcursionName.setText(excursionName);
        editExcursionDate.setText(excursionDate);

        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        editExcursionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateInfo = editExcursionDate.getText().toString();
                if (dateInfo.isEmpty()) {
                    dateInfo = "05/01/25";
                }
                try {
                    mCalenderStart.setTime(sdf.parse(dateInfo));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetailsActivity.this, excursionDatePicker, mCalenderStart.get(Calendar.YEAR), mCalenderStart.get(Calendar.MONTH), mCalenderStart.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        excursionDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalenderStart.set(Calendar.YEAR, year);
                mCalenderStart.set(Calendar.MONTH, month);
                mCalenderStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        mVacationListViewModel = new ViewModelProvider(this).get(VacationListViewModel.class);
        mExcursionlistViewModel = new ViewModelProvider(this).get(ExcursionListViewModel.class);

        mVacationListViewModel.getmAllVacations().observe(this, vacations -> {
            for (Vacation vacation : vacations) {
                if (vacation.getId() == vacationId) {
                    currVacation = vacation;
                }
            }
        });

        // Save Button

        Button saveButton = findViewById(R.id.save_excursion);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean safeDate = compareDates();
                if (safeDate) {
                    Intent intent = new Intent();
                    String excursionName = editExcursionName.getText().toString();
                    String excursionDate = editExcursionDate.getText().toString();
                    intent.putExtra("excursion_name", excursionName);
                    intent.putExtra("excursion_date", excursionDate);
                    int id = getIntent().getIntExtra("id", 0);
                    int vacationId = getIntent().getIntExtra("vacation_id", 0);
                    Excursion excursion = new Excursion(id, excursionName, excursionDate, vacationId, userId);
                    mExcursionlistViewModel.insert(excursion);

                    setResult(RESULT_OK, intent);
                    Toast.makeText(ExcursionDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        mExcursionlistViewModel.getmAllExcursions().observe(this, excursions -> {
            for (Excursion excursion : excursions) {
                if (excursion.getId() == id) {
                    currExcursion = excursion;
                }
            }
        });

        // Delete Button

        Button deleteButton = findViewById(R.id.delete_excursion);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExcursionlistViewModel.delete(currExcursion);
                Toast.makeText(ExcursionDetailsActivity.this,currExcursion.getExcursionName() + " was deleted.", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }

    private boolean compareDates() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        try {
            Date startDate = sdf.parse(currVacation.getStartDate());
            Date endDate = sdf.parse(currVacation.getEndDate());
            Date excursionDate = sdf.parse(editExcursionDate.getText().toString());
            if (excursionDate.before(startDate)) {
                Toast.makeText(ExcursionDetailsActivity.this, "Excursion date must be after the vacation's starting date", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if (excursionDate.after(endDate)) {
                Toast.makeText(ExcursionDetailsActivity.this, "Excursion date must be before the vacation's ending date", Toast.LENGTH_SHORT).show();
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

    private void updateLabelStart() {

        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        editExcursionDate.setText(sdf.format(mCalenderStart.getTime()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.excursion_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.notify) {

            String excursionNotifyDateString = editExcursionDate.getText().toString();
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date excursionNotifyDate = null;
            try {
                excursionNotifyDate = sdf.parse(excursionNotifyDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = excursionNotifyDate.getTime();
            Intent notifyIntent = new Intent(ExcursionDetailsActivity.this, MyReceiver.class);
            notifyIntent.putExtra("key", "Your excursion " + currExcursion.getExcursionName() + " is starting!");
            PendingIntent intentSender = PendingIntent.getBroadcast(ExcursionDetailsActivity.this, ++VacationListActivity.numAlert, notifyIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, intentSender);
        }
        return super.onOptionsItemSelected(item);
    }
}