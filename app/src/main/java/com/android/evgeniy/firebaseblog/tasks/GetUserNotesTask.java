package com.android.evgeniy.firebaseblog.tasks;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.android.evgeniy.firebaseblog.fragments.NotesFragment;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class GetUserNotesTask extends AsyncTask<DataSnapshot, Integer, ArrayList<UserNote>> {
    private Fragment fragment;

    public GetUserNotesTask(Fragment fragment) {
        this.fragment = fragment;
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

        if (fragment instanceof NotesFragment) {
            NotesFragment notesFragment = (NotesFragment) fragment;
            notesFragment.updateUserNotesAdapter(resultNotesList);
        }

    }
}
