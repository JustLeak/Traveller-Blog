package com.android.evgeniy.firebaseblog.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.models.UserDetails;
import com.android.evgeniy.firebaseblog.repositories.UserDetailsDao;
import com.android.evgeniy.firebaseblog.services.Checker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private UserDetails userDetails;
    private Button createAccountButton;
    private FirebaseAuth mAuth;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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
                            // Sign in success, update UI with the signed-in user's information
                            UserDetailsDao detailsDao = new UserDetailsDao();
                            detailsDao.setOneByUid(userDetails, mAuth.getUid());

                            Toast.makeText(RegistrationActivity.this, "Registration success.",
                                    Toast.LENGTH_SHORT).show();

                            mAuth.signInWithEmailAndPassword(userDetails.getEmail(), password);
                            startUserNotesActivity();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(RegistrationActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    private void startUserNotesActivity() {
        Intent intent = new Intent(this, UserNotesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Checker checker = new Checker();
        HashMap<String, String> inputs = getInputs();
        password = inputs.get("password");

        if (checker.isCorrectInputs(inputs)) {
            userDetails = UserDetails.builder().email(inputs.get("email"))
                    .firstName(inputs.get("firstName"))
                    .lastName(inputs.get("lastName"))
                    .age(Integer.valueOf(inputs.get("age")))
                    .gender(inputs.get("gender")).build();

            Registration();
        } else {
            Toast.makeText(RegistrationActivity.this, "Registration failed. Empty input.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private HashMap<String, String> getInputs() {
        HashMap<String, String> inputs = new HashMap<>();
        EditText editText = findViewById(R.id.et_email_reg);
        inputs.put("email", editText.getText().toString().trim());
        editText = findViewById(R.id.et_password_reg);
        inputs.put("password", editText.getText().toString().trim());
        editText = findViewById(R.id.et_name_reg);
        inputs.put("firstName", editText.getText().toString().trim());
        editText = findViewById(R.id.et_surname_reg);
        inputs.put("lastName", editText.getText().toString().trim());
        editText = findViewById(R.id.et_age_reg);
        inputs.put("age", editText.getText().toString().trim());

        RadioButton maleRadio = findViewById(R.id.rb_male_reg);
        if (maleRadio.isChecked())
            inputs.put("gender", "Male");
        else inputs.put("gender", "Female");

        return inputs;
    }
}
