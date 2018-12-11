package com.android.evgeniy.firebaseblog.tasks;

import android.os.AsyncTask;

import com.android.evgeniy.firebaseblog.adapters.UserNotesAdapter;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class GetUserNotesTask extends AsyncTask<Void, Integer, Void> {
    private DataSnapshot snapshot;
    private UserNotesAdapter adapter;

    public GetUserNotesTask(DataSnapshot snapshot, UserNotesAdapter adapter) {
        this.snapshot = snapshot;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ArrayList<UserNote> notesList = adapter.getUserNotesDao().getUserNotes();
        
        notesList.clear();
        for (DataSnapshot dataS : snapshot.getChildren()) {
            notesList.add(dataS.getValue(UserNote.class));
        }
        Collections.reverse(notesList);

        adapter.getUserNotesDao().setUserNotes(notesList);
        return null;
    }

    protected void onPostExecute(Void voids) {
        adapter.notifyDataSetChanged();
    }

    protected void onProgressUpdate(Integer... progress) {

    }
}
