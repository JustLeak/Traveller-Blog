package com.android.evgeniy.firebaseblog.services;

import com.android.evgeniy.firebaseblog.models.UserNote;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class NotesComparator implements Comparator<UserNote> {
    @Override
    public int compare(UserNote a, UserNote b) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
            if (dateFormat.parse(a.getDate()).getTime() < dateFormat.parse(b.getDate()).getTime()) {
                return 1;
            } else if (dateFormat.parse(a.getDate()).getTime() > dateFormat.parse(b.getDate()).getTime()) {
                return -1;
            } else if (timeFormat.parse(a.getTime()).getTime() < timeFormat.parse(b.getTime()).getTime()) {
                return 1;
            } else if (timeFormat.parse(a.getTime()).getTime() > timeFormat.parse(b.getTime()).getTime()) {
                return -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
