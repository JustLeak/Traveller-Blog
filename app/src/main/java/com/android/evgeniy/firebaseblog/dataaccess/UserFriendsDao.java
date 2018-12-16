package com.android.evgeniy.firebaseblog.dataaccess;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.android.evgeniy.firebaseblog.dataaccess.api.IUserFriendsDao;
import com.android.evgeniy.firebaseblog.fragments.MapFragment;
import com.android.evgeniy.firebaseblog.models.Friend;
import com.android.evgeniy.firebaseblog.tasks.GenericTask;
import com.android.evgeniy.firebaseblog.tasks.GetFriendsIdTask;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFriendsDao implements IUserFriendsDao {
    private final DatabaseReference mRef;
    private final String childName = "/Friends";
    private final String userId;


    public UserFriendsDao(String userId) {
        this.userId = userId;
        this.mRef = FirebaseDatabase.getInstance().getReference().child(userId + childName);
    }

    @Override
    public void getAll(final RecyclerView.Adapter adapter) {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTask<Friend> task = new GenericTask<>(adapter, Friend.class);
                task.execute(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAllFriendsId(final RecyclerView.Adapter adapter) {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GetFriendsIdTask task = new GetFriendsIdTask(adapter);
                task.execute(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAllFriendsId(final MapFragment mapFragment) {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GetFriendsIdTask task = new GetFriendsIdTask(mapFragment);
                task.execute(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void addOneByUid(Friend friend, String uid) {
        if (friend != null || !uid.isEmpty()) {
            mRef.push().setValue(friend);
        }
    }
}
