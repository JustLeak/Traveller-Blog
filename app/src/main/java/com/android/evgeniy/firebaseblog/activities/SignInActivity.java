package com.android.evgeniy.firebaseblog.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.evgeniy.firebaseblog.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    Button signInButton;
    Button registrationButton;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        signInButton = findViewById(R.id.btn_sign_in);
        signInButton.setOnClickListener(this);

        registrationButton = findViewById(R.id.btn_registration);
        registrationButton.setOnClickListener(this);
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
        EditText editText = findViewById(R.id.et_email);
        email = editText.getText().toString();
        editText = findViewById(R.id.et_password);
        password = editText.getText().toString();

        switch (v.getId()) {
            case R.id.btn_sign_in:
                SignIn();
                break;
            case R.id.btn_registration:
                startSignUpActivity();
                break;
        }
    }

    public void SignIn() {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignInActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();

                            startUserNotesActivity();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }



    private void startUserNotesActivity(){
        Intent intent = new Intent(this, UserNotesActivity.class);
        startActivity(intent);
    }

    private void startSignUpActivity(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
