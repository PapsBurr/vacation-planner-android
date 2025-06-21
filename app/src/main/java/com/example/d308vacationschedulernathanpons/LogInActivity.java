package com.example.d308vacationschedulernathanpons;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.d308vacationschedulernathanpons.model.User;
import com.example.d308vacationschedulernathanpons.repo.VacationRepository;
import com.example.d308vacationschedulernathanpons.viewmodel.LogInViewModel;
import com.example.d308vacationschedulernathanpons.viewmodel.VacationListViewModel;

import java.util.ArrayList;
import java.util.List;

public class LogInActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button logInButton;
    private Button registerButton;
    private HashGenerator hashGenerator;
    private static User mUser;
    private static List<User> mAllUsers;

    private LogInViewModel mLogInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.log_in);

        usernameEditText = findViewById(R.id.username_edit);
        passwordEditText = findViewById(R.id.password_edit);
        logInButton = findViewById(R.id.log_in_button);
        registerButton = findViewById(R.id.register_button);
        mAllUsers = new ArrayList<>();

        mLogInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);
        hashGenerator = new HashGenerator();

        mLogInViewModel.getmAllUsers().observe(this, users -> {
            mAllUsers = users;
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                boolean accountExists = false;

                if (!mAllUsers.isEmpty()) {
                    for (User user : mAllUsers) {
                        if (user.getUsername().equals(username)) {
                            if (hashGenerator.AuthorizeUser(password, user.getSalt(), user.getPassword())) {
                                accountExists = true;
                                Intent intent = new Intent(LogInActivity.this, VacationListActivity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                            }
                        }
                    }
                }
                if (!accountExists) {
                    Toast.makeText(LogInActivity.this, "Username/Password does not exist", Toast.LENGTH_LONG).show();
                }


            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLogInViewModel.getmAllUsers().observe(this, users -> {
            mAllUsers = users;
        });
    }
}