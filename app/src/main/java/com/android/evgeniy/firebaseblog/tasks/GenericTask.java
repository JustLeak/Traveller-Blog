package com.android.evgeniy.firebaseblog.tasks;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.android.evgeniy.firebaseblog.adapters.FriendsRecyclerAdapter;
import com.android.evgeniy.firebaseblog.adapters.NotesRecyclerAdapter;
import com.android.evgeniy.firebaseblog.models.Friend;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.google.firebase.database.DataSnapshot;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class GenericTask<T> extends AsyncTask<DataSnapshot, Integer, ArrayList<T>> {
    private RecyclerView.Adapter adapter;
    private Class<T> genericType;

    public GenericTask(RecyclerView.Adapter adapter, Class<T> type) {
        this.adapter = adapter;
        genericType = type;
    }

    @Override
    protected ArrayList<T> doInBackground(DataSnapshot... snapshots) {
        ArrayList<T> friends = new ArrayList<>();
        for (DataSnapshot dataS : snapshots[0].getChildren()) {
            Friend friend =(Friend) dataS.getValue(genericType);
            friend.setKey(dataS.getKey());
            friends.add((T)friend);
        }
        Collections.reverse(friends);

        return friends;
    }

    @Override
    protected void onPostExecute(ArrayList<T> resultList) {
        super.onPostExecute(resultList);

        if (genericType.equals(Friend.class)) {
            FriendsRecyclerAdapter friendsRecyclerAdapter = (FriendsRecyclerAdapter) adapter;
            friendsRecyclerAdapter.update((ArrayList<Friend>) resultList);
        } else if (genericType.equals(UserNote.class)) {
            NotesRecyclerAdapter notesRecyclerAdapter = (NotesRecyclerAdapter) adapter;
            /*notesRecyclerAdapter.update((ArrayList<UserNote>) resultList);*/
        }
    }

    private Class<T> getGenericClass() {
        Class<T> type = null;
        Type mySuperclass = getClass().getGenericSuperclass();
        Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[0];
        String className = tType.toString().split(" ")[1];
        try {
            type = (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return type;
    }
}
