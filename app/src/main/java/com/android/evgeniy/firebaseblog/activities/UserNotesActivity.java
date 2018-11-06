package com.android.evgeniy.firebaseblog.activities;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserNotesActivity extends AppCompatActivity {

    private ListView items;
    private TextView note;
    private TextView date;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private ItemsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notes);

        adapter = new ItemsAdapter();

        items = findViewById(R.id.items);
        note = findViewById(R.id.note);
        date = findViewById(R.id.date);

        items.setAdapter(adapter);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        /*notesView = findViewById(R.id.lw_notes);*/

        myRef = FirebaseDatabase.getInstance().getReference().child(user.getUid() + "/Notes");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {









/*                     Date date = new Date();
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

                UserNote userNote = UserNote.builder().date(simpleDateFormat.format(date)).text("First note").build();
                UserNotesDao userNotesDao = new UserNotesDao();
                userNotesDao.addOneByUid(userNote, user.getUid());*/


                for (DataSnapshot dataS : dataSnapshot.getChildren()) {
                    /*notesView.setText(notesView.getText() + "\n" + dataS.getKey());
                    notesView.setText(notesView.getText() + "\n" + dataS.getValue(UserNote.class).toString());*/
                    adapter.add(dataS.getValue(UserNote.class));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private class ItemsAdapter extends ArrayAdapter<UserNote> {
        ItemsAdapter() {
            super(UserNotesActivity.this, R.layout.item);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final View view = getLayoutInflater().inflate(R.layout.item, null);
            final UserNote item = getItem(position);
            ((TextView) view.findViewById(R.id.note)).setText(item.getText());
            ((TextView) view.findViewById(R.id.date)).setText(item.getDate());
            return view;
        }
    }
}
