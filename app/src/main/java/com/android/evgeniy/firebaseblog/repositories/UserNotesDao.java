package com.android.evgeniy.firebaseblog.repositories;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

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

public class UserNotesDao implements IUserNotesDao {
    private final DatabaseReference mRef;
    private final String childName = "/Notes";
    private final FirebaseUser user;

    @Override
    public void getAll(final Fragment fragment) {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GetUserNotesTask task = new GetUserNotesTask(fragment);
                task.execute(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public UserNotesDao() {
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.mRef = FirebaseDatabase.getInstance().getReference().child(user.getUid() + childName);
    }

    @Override
    public void addOneByUid(UserNote userNote, String uid) {
        if (userNote != null || !uid.isEmpty()) {
            mRef.push().setValue(userNote);
        }
    }


}
