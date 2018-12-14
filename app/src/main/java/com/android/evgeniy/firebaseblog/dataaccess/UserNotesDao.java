package com.android.evgeniy.firebaseblog.dataaccess;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.android.evgeniy.firebaseblog.models.UserNote;
import com.android.evgeniy.firebaseblog.dataaccess.api.IUserNotesDao;
import com.android.evgeniy.firebaseblog.tasks.GetNotesTask;
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
    public void getAll(final RecyclerView.Adapter adapter) {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GetNotesTask task = new GetNotesTask(adapter);
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
