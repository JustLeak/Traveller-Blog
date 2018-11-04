package com.android.evgeniy.firebaseblog.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.models.UserDetails;
import com.android.evgeniy.firebaseblog.services.Checker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    UserDetails userDetails;
    Button createAccountButton;
    private FirebaseAuth mAuth;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        createAccountButton = findViewById(R.id.btn_create_acc);
        createAccountButton.setOnClickListener(this);
    }

    public void Registration() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegistrationActivity.this, "Registration success.",
                                    Toast.LENGTH_SHORT).show();

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
        getInputs();
        Checker checker = new Checker();
        if (checker.isCorrectUserDetails(userDetails) && !password.isEmpty()) {
            Registration();
        }

    }

    private void getInputs() {
        EditText editText = findViewById(R.id.et_email_reg);
        email = editText.getText().toString();
        editText = findViewById(R.id.et_password_reg);
        password = editText.getText().toString();

        userDetails.setEmail(email);

        editText = findViewById(R.id.et_name_reg);
        userDetails.setName(editText.getText().toString());

        editText = findViewById(R.id.et_surname_reg);
        userDetails.setSurname(editText.getText().toString());

        editText = findViewById(R.id.et_age_reg);
        userDetails.setAge(Integer.valueOf(editText.getText().toString()));

        RadioGroup radioGroup = findViewById(R.id.rg_gender_reg);
        if (radioGroup.getCheckedRadioButtonId() == R.id.rb_male_reg)
            userDetails.setGender("Male");
        else userDetails.setGender("Female");
    }
}
