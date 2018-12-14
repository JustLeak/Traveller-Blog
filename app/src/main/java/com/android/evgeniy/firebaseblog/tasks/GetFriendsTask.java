package com.android.evgeniy.firebaseblog.tasks;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.android.evgeniy.firebaseblog.adapters.FriendsRecyclerAdapter;
import com.android.evgeniy.firebaseblog.models.Friend;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class GetFriendsTask extends AsyncTask<DataSnapshot, Integer, ArrayList<Friend>> {
    private RecyclerView.Adapter adapter;

    public GetFriendsTask(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected ArrayList<Friend> doInBackground(DataSnapshot... snapshots) {
        ArrayList<Friend> friends = new ArrayList<>();

        for (DataSnapshot dataS : snapshots[0].getChildren()) {
            friends.add(dataS.getValue(Friend.class));
        }
        Collections.reverse(friends);

        return friends;
    }

    @Override
    protected void onPostExecute(ArrayList<Friend> resultFriendsList) {
        super.onPostExecute(resultFriendsList);

        if (adapter instanceof FriendsRecyclerAdapter) {
            FriendsRecyclerAdapter friendsRecyclerAdapter = (FriendsRecyclerAdapter) adapter;
            friendsRecyclerAdapter.update(resultFriendsList);
        }
    }
}
