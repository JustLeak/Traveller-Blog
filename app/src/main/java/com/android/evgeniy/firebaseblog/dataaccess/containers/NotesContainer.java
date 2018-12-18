package com.android.evgeniy.firebaseblog.dataaccess.containers;

import com.android.evgeniy.firebaseblog.models.UserNote;
import com.android.evgeniy.firebaseblog.services.NotesComparator;

import java.util.ArrayList;
import java.util.Collections;

public final class NotesContainer extends ArrayList<UserNote> {

    public void addNote(UserNote note) {
        super.add(note);
        Collections.sort(super.subList(0, super.size() - 1), new NotesComparator());
    }

    public int changeNote(UserNote newNote, String key) {
        int index = findNoteIndexByKey(key);
        super.set(index, newNote);
        return index;
    }

    public int deleteNote(String key) {
        int index = findNoteIndexByKey(key);
        super.remove(index);
        return index;
    }

    private int findNoteIndexByKey(String key) {
        for (int i = 0; i < super.size(); i++) {
            if (super.get(i).getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }
}
