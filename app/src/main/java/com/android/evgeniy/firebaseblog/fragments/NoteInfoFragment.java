package com.android.evgeniy.firebaseblog.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;

public class NoteInfoFragment extends Fragment {

    private TextView noteTv;
    private TextView dateTv;
    private TextView timeTv;
    private TextView emailTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noteinfo, container, false);

        noteTv = view.findViewById(R.id.note_tv);
        dateTv = view.findViewById(R.id.date_tv);
        timeTv = view.findViewById(R.id.time_tv);
        emailTv = view.findViewById(R.id.email_tv);






        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
