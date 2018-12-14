package com.android.evgeniy.firebaseblog.tasks;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.android.evgeniy.firebaseblog.adapters.NotesRecyclerAdapter;
import com.android.evgeniy.firebaseblog.models.Friend;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class GetFriendsIdTask extends AsyncTask<DataSnapshot, Integer, ArrayList<String>> {
    private RecyclerView.Adapter adapter;

    public GetFriendsIdTask(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected ArrayList<String> doInBackground(DataSnapshot... snapshots) {
        ArrayList<String> friendsId = new ArrayList<>();
        Friend friend;
        for (DataSnapshot dataS : snapshots[0].getChildren()) {
            friend = dataS.getValue(Friend.class);
            friendsId.add(friend.getId());
        }
        Collections.reverse(friendsId);

        return friendsId;
    }

    @Override
    protected void onPostExecute(ArrayList<String> resultIdList) {
        super.onPostExecute(resultIdList);

        if (adapter instanceof NotesRecyclerAdapter) {
            NotesRecyclerAdapter notesRecyclerAdapter = (NotesRecyclerAdapter) adapter;
            notesRecyclerAdapter.setListeners(resultIdList);
        }
    }
}
