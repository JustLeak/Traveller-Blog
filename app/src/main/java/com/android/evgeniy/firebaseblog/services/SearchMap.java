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


public class SearchMap implements ISearchMap {
    private DatabaseReference reference;
    private Context viewContext;

    private String userId;

    public SearchMap(String userId) {
        viewContext = null;
        this.userId = userId;
        reference = FirebaseDatabase.getInstance().getReference().child("/SearchMap");
    }

    @Override
    public void findFriendByEmail(final String email, Context viewContext) {
        this.viewContext = viewContext;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FriendSearchTask task = new FriendSearchTask(SearchMap.this, userId,email);
                task.execute(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void addMapItem(String email){
        Friend friend = new Friend();
        friend.setId(userId);
        friend.setEmail(email);
        reference.push().setValue(friend);
    }

    public void showResult(Boolean result){
        if(result) showToast("Added to your friends");
        else showToast("User is not found");
    }

    private void showToast(String str) {
        Toast.makeText(viewContext, str, Toast.LENGTH_SHORT).show();
    }
}
