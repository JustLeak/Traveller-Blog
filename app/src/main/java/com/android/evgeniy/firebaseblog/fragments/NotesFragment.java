package com.android.evgeniy.firebaseblog.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.adapters.UserNotesAdapter;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.android.evgeniy.firebaseblog.repositories.UserNotesDao;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class NotesFragment extends Fragment {
    private View view;
    private ListView items;
    private Button button;

    private UserNotesDao userNotesDao;
    private UserNotesAdapter userNotesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notes, container, false);
        items = view.findViewById(R.id.items);
        button = view.findViewById(R.id.btn_new_record);

        items.setAdapter(userNotesAdapter);
        userNotesDao.getAll(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateNoteFragment()).commit();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userNotesDao = new UserNotesDao();
        userNotesAdapter = new UserNotesAdapter(getLayoutInflater());
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void updateUserNotesAdapter(ArrayList<UserNote> userNotes) {
        userNotesAdapter.setUserNotes(userNotes);
        userNotesAdapter.notifyDataSetChanged();
    }
}
