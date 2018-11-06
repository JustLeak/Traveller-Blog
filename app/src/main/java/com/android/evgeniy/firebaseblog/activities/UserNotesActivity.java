package com.android.evgeniy.firebaseblog.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.models.UserDetails;
import com.android.evgeniy.firebaseblog.repositories.UserDetailsDao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserNotesActivity extends AppCompatActivity {

    UserDetails userDetails;
    private FirebaseAuth mAuth;
    DatabaseReference myRef;

    private List<String> noteTextList;

    private ListView noteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notes);

        noteListView = (ListView) findViewById(R.id.note_text);

        myRef = FirebaseDatabase.getInstance().getReference();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                };
                noteTextList = dataSnapshot.child("Notes").getValue(t);

                UserDetailsDao detailsDao = new UserDetailsDao();


                UserDetails userDetails = dataSnapshot.child("Details").getValue(UserDetails.class);

                detailsDao.setOneByUid(userDetails, user.getUid());

                System.out.println(userDetails.toString());
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUI() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, noteTextList);

        noteListView.setAdapter(adapter);
    }
}
