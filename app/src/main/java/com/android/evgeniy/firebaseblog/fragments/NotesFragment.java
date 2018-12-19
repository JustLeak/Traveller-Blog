package com.android.evgeniy.firebaseblog.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.adapters.ClickNoteRecyclerAdapter;
import com.android.evgeniy.firebaseblog.adapters.touchHelpers.SimpleItemTouchHelperCallback;

public class NotesFragment extends Fragment implements ClickNoteRecyclerAdapter.OnItemClickListener {
    private View view;
    private RecyclerView notesRecyclerView;
    private TextView noDataTextView;
    private FloatingActionButton button;
    private CardView cardView;
    private ItemTouchHelper mItemTouchHelper;

    private ClickNoteRecyclerAdapter clickNoteRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notes, container, false);
        notesRecyclerView = view.findViewById(R.id.items);
        button = view.findViewById(R.id.btn_new_record);
        noDataTextView = view.findViewById(R.id.noDataTextView);
        cardView = view.findViewById(R.id.card_view);

        if (getArguments() != null) {
            if (getArguments().containsKey("userId")) {
                clickNoteRecyclerAdapter = new ClickNoteRecyclerAdapter(getLayoutInflater(), this, getArguments().getString("userId"));
            } else {
                clickNoteRecyclerAdapter = new ClickNoteRecyclerAdapter(getLayoutInflater(), this);
            }
        } else {
            clickNoteRecyclerAdapter = new ClickNoteRecyclerAdapter(getLayoutInflater(), this);
        }

        notesRecyclerView.setAdapter(clickNoteRecyclerAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        notesRecyclerView.setHasFixedSize(true);



        notesRecyclerView.getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (clickNoteRecyclerAdapter.getNotesContainer().size() != 0) {
                    noDataTextView.setVisibility(View.INVISIBLE);
                } else noDataTextView.setVisibility(View.VISIBLE);
            }
        });


        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(clickNoteRecyclerAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(notesRecyclerView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateNoteFragment()).addToBackStack(null).commit();
            }
        });


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle arguments = new Bundle();
        arguments.putString("lat", clickNoteRecyclerAdapter.getNotesContainer().get(position).getLocation().getLat());
        arguments.putString("lng", clickNoteRecyclerAdapter.getNotesContainer().get(position).getLocation().getLng());
        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(arguments);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, mapFragment).addToBackStack(null).commit();
    }

}
