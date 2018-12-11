package com.android.evgeniy.firebaseblog.repositories;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android.evgeniy.firebaseblog.adapters.UserNotesAdapter;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.android.evgeniy.firebaseblog.repositories.interfaces.IUserNotesDao;
import com.android.evgeniy.firebaseblog.tasks.GetUserNotesTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public List<UserNote> getAll() {
        return userNotes;
    }

    @Override
    public UserNote getOneById(int id) {
        return userNotes.get(id);
    }

    public int getUserNotesCount() {
        return userNotes.size();
    }

    public ArrayList<UserNote> getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(ArrayList<UserNote> userNotes) {
        this.userNotes = userNotes;
    }
}
