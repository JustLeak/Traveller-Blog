package com.android.evgeniy.firebaseblog.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.models.UserNote;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class NotesFragment extends Fragment {

    private View view;
    private ListView items;
    private Button button;
    private ItemsAdapter adapter;
    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notes, container, false);

        items = view.findViewById(R.id.items);
        button = view.findViewById(R.id.btn_new_record);
        adapter = new ItemsAdapter();
        items.setAdapter(adapter);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference().child(user.getUid() + "/Notes");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<UserNote> notes = new ArrayList<>();
                for (DataSnapshot dataS : dataSnapshot.getChildren()) {
                    notes.add(dataS.getValue(UserNote.class));
                }

                Collections.reverse(notes);

                for (UserNote note : notes) {
                    adapter.add(note);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateNoteFragment()).commit();
            }
        });
        return view;
    }


    private class ItemsAdapter extends ArrayAdapter<UserNote> {
        ItemsAdapter() {
            super(view.getContext(), R.layout.item);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            @SuppressLint({"ViewHolder", "InflateParams"}) final View view = getLayoutInflater().inflate(R.layout.item, null);
            final UserNote item = getItem(position);
            ((TextView) view.findViewById(R.id.note)).setText(item.getText());
            ((TextView) view.findViewById(R.id.date)).setText(item.getDate());
            return view;
        }
    }
}
