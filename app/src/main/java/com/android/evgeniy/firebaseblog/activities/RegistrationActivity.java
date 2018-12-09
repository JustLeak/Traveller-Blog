package com.android.evgeniy.firebaseblog.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.models.UserDetails;
import com.android.evgeniy.firebaseblog.repositories.UserDetailsDao;
import com.android.evgeniy.firebaseblog.services.Checker;
import com.android.evgeniy.firebaseblog.services.UserDetailsSetter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private UserDetails userDetails;
    private Button createAccountButton;
    private FirebaseAuth mAuth;
    private String password;

    private EditText mUsername;
    private EditText mPassword;
    private EditText firstName;
    private EditText lastName;
    private EditText age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mUsername = findViewById(R.id.et_email_reg);
        mPassword = findViewById(R.id.et_password_reg);
        firstName = findViewById(R.id.et_name_reg);
        lastName = findViewById(R.id.et_surname_reg);
        age = findViewById(R.id.et_age_reg);

        userDetails = new UserDetails();

        createAccountButton = findViewById(R.id.btn_create_acc);
        createAccountButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    public void Registration() {
        mAuth.createUserWithEmailAndPassword(userDetails.getEmail(), password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserDetailsDao detailsDao = new UserDetailsDao();
                            detailsDao.setOneByUid(userDetails, mAuth.getUid());

                            Toast.makeText(RegistrationActivity.this, "Registration success.",
                                    Toast.LENGTH_SHORT).show();

                            mAuth.signInWithEmailAndPassword(userDetails.getEmail(), password);
                            startUserNotesActivity();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void startUserNotesActivity() {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Checker checker = new Checker();
        HashMap<String, String> inputs = getInputs();

        if (checker.isCorrectInputs(inputs)) {
            password = inputs.get("password");
            userDetails = UserDetailsSetter.set(inputs);
            Registration();
        } else {
            Toast.makeText(RegistrationActivity.this, "Please, fill in all fields.", Toast.LENGTH_SHORT).show();
            addErrorsToFields(checker.getResultMap());
        }
    }

    private HashMap<String, String> getInputs() {
        HashMap<String, String> inputs = new HashMap<>();

        inputs.put("email", mUsername.getText().toString().trim());
        inputs.put("password", mPassword.getText().toString().trim());
        inputs.put("firstName", firstName.getText().toString().trim());
        inputs.put("lastName", lastName.getText().toString().trim());
        inputs.put("age", age.getText().toString().trim());

        RadioButton maleRadio = findViewById(R.id.rb_male_reg);
        if (maleRadio.isChecked())
            inputs.put("gender", "Male");
        else inputs.put("gender", "Female");

        return inputs;
    }

    private void addErrorsToFields(HashMap<String, Boolean> inputResults) {
        for (String key : inputResults.keySet()) {
            if (!inputResults.get(key)) {
                {
                    switch (key) {
                        case "email": {
                            mUsername.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_dark), PorterDuff.Mode.SRC_ATOP);
                        }
                        case "password": {
                            mPassword.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_dark), PorterDuff.Mode.SRC_ATOP);
                        }
                        case "firstName": {
                            firstName.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_dark), PorterDuff.Mode.SRC_ATOP);
                        }
                        case "lastName": {
                            lastName.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_dark), PorterDuff.Mode.SRC_ATOP);
                        }
                        case "age": {
                            age.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_dark), PorterDuff.Mode.SRC_ATOP);
                        }
                    }
                }
            }
        }
    }
}
