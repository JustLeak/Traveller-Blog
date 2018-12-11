package com.android.evgeniy.firebaseblog.repositories;

import android.support.annotation.NonNull;

import com.android.evgeniy.firebaseblog.adapters.FriendsAdapter;
import com.android.evgeniy.firebaseblog.models.Friend;
import com.android.evgeniy.firebaseblog.models.User;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.android.evgeniy.firebaseblog.repositories.interfaces.IUserFriendsDao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class UserFriendsDao implements IUserFriendsDao {
    private final DatabaseReference mRef;
    private final String childName = "/Friends";
    private final FirebaseUser user;
    private ArrayList<Friend> friends;

    public UserFriendsDao(final FriendsAdapter adapter) {
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.mRef = FirebaseDatabase.getInstance().getReference().child(user.getUid() + childName);
        friends = new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friends.clear();
                for (DataSnapshot dataS : dataSnapshot.getChildren()) {
                    friends.add(dataS.getValue(Friend.class));
                }
                Collections.reverse(friends);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void addOneByUid(Friend friend) {
        if (friend != null) {
            mRef.push().setValue(friend);
        }
    }

    @Override
    public ArrayList<Friend> getAll() {
        return friends;
    }

    @Override
    public Friend getOneById(int id) {
        return friends.get(id);
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    public String getFriendIdByEmail(String email){
        for (Friend friend :friends) {
            if (friend.getEmail().equals(email)) return friend.getId();
        }
        return null;
    }
}
