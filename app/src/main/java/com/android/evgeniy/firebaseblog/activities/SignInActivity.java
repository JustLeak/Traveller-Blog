package com.android.evgeniy.firebaseblog.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.constants.Settings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private FirebaseAuth mAuth;
    private SharedPreferences mSettings;

    private TextInputLayout mUsernameLayout;
    private TextInputLayout mPasswordLayout;
    private Button signInButton;
    private Button registrationButton;
    private EditText mUsername;
    private EditText mPassword;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        mSettings = getSharedPreferences(Settings.APP_SETTINGS, Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mUsernameLayout = findViewById(R.id.login_layout);
        mPasswordLayout = findViewById(R.id.password_layout);

        signInButton = findViewById(R.id.btn_sign_in);
        signInButton.setOnClickListener(this);

        registrationButton = findViewById(R.id.btn_registration);
        registrationButton.setOnClickListener(this);

        mUsername = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);

        mUsername.setOnFocusChangeListener(this);
        mPassword.setOnFocusChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startUserNotesActivity();
        }
    }

    @Override
    public void onClick(View v) {
        email = mUsername.getText().toString();
        password = mPassword.getText().toString();

        switch (v.getId()) {
            case R.id.btn_sign_in:
                if (email.isEmpty() || password.isEmpty()) {

                    Snackbar.make(v, "Please, fill in all fields.", Snackbar.LENGTH_SHORT).show();

                    if (email.isEmpty()) {
                        mUsernameLayout.setErrorEnabled(true);
                        mUsernameLayout.setError(getResources().getString(R.string.login_error));
                    }
                    if (password.isEmpty()) {
                        mPasswordLayout.setErrorEnabled(true);
                        mPasswordLayout.setError(getResources().getString(R.string.password_error));
                    }
                } else
                    SignIn();

                break;
            case R.id.btn_registration:
                startRegistrationActivity();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!mSettings.contains(Settings.APP_SETTINGS_FRIENDNOTES)) {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putBoolean(Settings.APP_SETTINGS_FRIENDNOTES, false);
            editor.apply();
        }
    }

    public void SignIn() {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(findViewById(android.R.id.content), "Authentication success.", Snackbar.LENGTH_SHORT).show();

                            startUserNotesActivity();
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "Authentication failed.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void startUserNotesActivity() {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }

    private void startRegistrationActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v != mUsername && mUsername.getText().toString().isEmpty()) {
            mPasswordLayout.setErrorEnabled(false);
            mUsernameLayout.setErrorEnabled(true);
            mUsernameLayout.setError(getResources().getString(R.string.login_error));
        } else if (v != mPassword && mPassword.getText().toString().isEmpty()) {
            mUsernameLayout.setErrorEnabled(false);
            mPasswordLayout.setErrorEnabled(true);
            mPasswordLayout.setError(getResources().getString(R.string.password_error));
        } else {
            mUsernameLayout.setErrorEnabled(false);
            mPasswordLayout.setErrorEnabled(false);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) hideKeyboard();
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}
