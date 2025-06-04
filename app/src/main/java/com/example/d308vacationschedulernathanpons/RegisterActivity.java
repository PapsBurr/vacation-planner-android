package com.example.d308vacationschedulernathanpons;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.d308vacationschedulernathanpons.viewmodel.LogInViewModel;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditTextReg;
    private EditText passwordEditTextReg;
    private Button registerButtonReg;
    private static User mUser;
    private static List<User> mAllUsers;

    private LogInViewModel mLogInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameEditTextReg = findViewById(R.id.username_register_edit);
        passwordEditTextReg = findViewById(R.id.password_register_edit);
        registerButtonReg = findViewById(R.id.create_account_register_button);

        mLogInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);

        registerButtonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean userExists = false;
                for (User user : mAllUsers) {
                    if (user.getUsername().equals(usernameEditTextReg.getText().toString())) {
                        userExists = true;
                    }
                }

                if (!userExists) {
                    User newUser = new User(0, usernameEditTextReg.getText().toString(), passwordEditTextReg.getText().toString());
                    mLogInViewModel.insert(newUser);
                    Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "User created!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(RegisterActivity.this, "User already exists!", Toast.LENGTH_LONG).show();
                }
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