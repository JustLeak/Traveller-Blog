package com.android.evgeniy.firebaseblog.dataaccess;

import com.android.evgeniy.firebaseblog.models.UserNote;
import com.android.evgeniy.firebaseblog.services.NotesComparator;

import java.util.ArrayList;
import java.util.Collections;

public final class NotesContainer {
    private ArrayList<UserNote> notes;

    public NotesContainer() {
        notes = new ArrayList<>();
    }

    public ArrayList<UserNote> getNotes() {
        return notes;
    }

    public void addNote(UserNote note) {
        notes.add(note);
        Collections.sort(notes, new NotesComparator());
    }

    public int changeNote(UserNote newNote, String key) {
        int index = findNoteIndexByKey(key);
        notes.set(index, newNote);
        return index;
    }

    public int deleteNote(String key) {
        int index = findNoteIndexByKey(key);
        notes.remove(index);
        return index;
    }

    private int findNoteIndexByKey(String key) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }
}
