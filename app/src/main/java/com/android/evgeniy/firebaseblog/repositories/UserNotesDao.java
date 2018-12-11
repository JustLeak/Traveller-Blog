package com.android.evgeniy.firebaseblog.repositories;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android.evgeniy.firebaseblog.adapters.UserNotesAdapter;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.android.evgeniy.firebaseblog.repositories.interfaces.IUserNotesDao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class UserNotesDao implements IUserNotesDao {
    private final DatabaseReference mRef;
    private final String childName = "/Notes";
    private final FirebaseUser user;
    private ArrayList<UserNote> userNotes;

    public UserNotesDao(final UserNotesAdapter userNotesAdapter) {
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.mRef = FirebaseDatabase.getInstance().getReference().child(user.getUid() + childName);
        userNotes = new ArrayList<>();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GetUserNotesTask task = new GetUserNotesTask(dataSnapshot, userNotesAdapter);
                task.execute();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void addOneByUid(UserNote userNote, String uid) {
        if (userNote != null || !uid.isEmpty()) {
            mRef.push().setValue(userNote);
        }
    }

    @Override
    public ArrayList<UserNote> getAll() {
        return userNotes;
    }

    @Override
    public UserNote getOneById(int id) {
        return userNotes.get(id);
    }

    @Override
    public int getCount() {
        return userNotes.size();
    }

    private class GetUserNotesTask extends AsyncTask<Void, Integer, Void> {
        private DataSnapshot snapshot;
        private UserNotesAdapter adapter;

        GetUserNotesTask(DataSnapshot snapshot, UserNotesAdapter adapter) {
            this.snapshot = snapshot;
            this.adapter = adapter;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            userNotes.clear();
            for (DataSnapshot dataS : snapshot.getChildren()) {
                userNotes.add(dataS.getValue(UserNote.class));
            }
            Collections.reverse(userNotes);
            return null;
        }

        protected void onPostExecute(Void voids) {
            adapter.notifyDataSetChanged();
        }

        protected void onProgressUpdate(Integer... progress) {

        }
    }
}
