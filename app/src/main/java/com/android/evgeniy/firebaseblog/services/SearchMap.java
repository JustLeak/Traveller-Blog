package com.android.evgeniy.firebaseblog.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android.evgeniy.firebaseblog.models.Friend;
import com.android.evgeniy.firebaseblog.services.api.ISearchMap;
import com.android.evgeniy.firebaseblog.tasks.FriendSearchTask;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SearchMap implements ISearchMap {
    private DatabaseReference reference;
    private Context viewContext;
    private ValueEventListener myEmailListener;
    private ValueEventListener searchMapListener;
    private ArrayList<Friend> friends;

    private String userId;

    public SearchMap(String userId) {
        viewContext = null;
        this.userId = userId;
    }

    @Override
    public void findFriendByEmail(final String email, final Context viewContext, ArrayList<Friend> friends) {
        this.friends = friends;
        this.viewContext = viewContext;
        reference = FirebaseDatabase.getInstance().getReference().child(userId + "/Details" + "/email");
        myEmailListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String myEmail = (String) dataSnapshot.getValue();
                if (email.equals(myEmail)) {
                    showToast("It is you(=");
                } else if (isInFriendList(email)) showToast(email.concat(" is already on your friend list."));
                else findFriend(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(myEmailListener);
    }

    private Boolean isInFriendList(String email) {
        for (Friend friend : friends) {
            if (friend.getEmail().equals(email)) return true;
        }
        return false;
    }

    @Override
    public void addMapItem(String email) {
        Friend friend = new Friend();
        friend.setId(userId);
        friend.setEmail(email);
        reference.push().setValue(friend);
    }

    private void findFriend(final String email) {
        reference.removeEventListener(myEmailListener);
        reference = FirebaseDatabase.getInstance().getReference().child("/SearchMap");
        searchMapListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FriendSearchTask task = new FriendSearchTask(SearchMap.this, userId, email);
                task.execute(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(searchMapListener);
    }

    public void showToast(String str) {
        Toast.makeText(viewContext, str, Toast.LENGTH_SHORT).show();
    }
}
