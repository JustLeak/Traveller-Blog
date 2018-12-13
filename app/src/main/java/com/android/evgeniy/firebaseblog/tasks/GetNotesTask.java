package com.android.evgeniy.firebaseblog.tasks;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.android.evgeniy.firebaseblog.adapters.NotesRecyclerAdapter;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class GetNotesTask extends AsyncTask<DataSnapshot, Integer, ArrayList<UserNote>> {
    private RecyclerView.Adapter adapter;

    public GetNotesTask(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected ArrayList<UserNote> doInBackground(DataSnapshot... snapshots) {
        ArrayList<UserNote> notesList = new ArrayList<>();

        for (DataSnapshot dataS : snapshots[0].getChildren()) {
            notesList.add(dataS.getValue(UserNote.class));
        }
        Collections.reverse(notesList);

        return notesList;
    }

    @Override
    protected void onPostExecute(ArrayList<UserNote> resultNotesList) {
        super.onPostExecute(resultNotesList);

        if (adapter instanceof NotesRecyclerAdapter) {
            NotesRecyclerAdapter notesRecyclerAdapter = (NotesRecyclerAdapter) adapter;
            notesRecyclerAdapter.update(resultNotesList);
        }
    }
}
