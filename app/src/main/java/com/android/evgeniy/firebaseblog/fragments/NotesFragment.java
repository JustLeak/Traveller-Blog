package com.android.evgeniy.firebaseblog.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.adapters.ClickNoteRecyclerAdapter;
import com.android.evgeniy.firebaseblog.repositories.UserNotesDao;

public class NotesFragment extends Fragment implements ClickNoteRecyclerAdapter.OnItemClickListener{
    private View view;
    private RecyclerView items;
    private Button button;

    private UserNotesDao userNotesDao;
    private ClickNoteRecyclerAdapter clickNoteRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notes, container, false);
        items = view.findViewById(R.id.items);
        button = view.findViewById(R.id.btn_new_record);

        items.setAdapter(clickNoteRecyclerAdapter);
        items.setLayoutManager(new LinearLayoutManager(view.getContext()));
        items.setHasFixedSize(true);

        userNotesDao.getAll(clickNoteRecyclerAdapter);

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
        clickNoteRecyclerAdapter = new ClickNoteRecyclerAdapter(getLayoutInflater(), this);
    }
    

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(view.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}
