package com.android.evgeniy.firebaseblog.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.adapters.FriendsAdapter;
import com.android.evgeniy.firebaseblog.models.Friend;
import com.android.evgeniy.firebaseblog.repositories.UserFriendsDao;

public class FriendsFragment extends android.support.v4.app.Fragment {
    private View view;
    private ListView friendsList;
    private Button button;

    private FriendsAdapter friendsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends, container, false);
        button = view.findViewById(R.id.add_friend_btn);

        friendsList = view.findViewById(R.id.friends);
        friendsAdapter = new FriendsAdapter(getLayoutInflater());

        friendsList.setAdapter(friendsAdapter);
        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView email = (TextView)view.findViewById(R.id.email);
                TextView id = (TextView)view.findViewById(R.id.friend_id);
                showToast(email.getText().toString()+ " " + id.getText().toString());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend friend = new Friend();
                friend.setEmail("nikitamatusevich@mail.ru");
                friend.setId("matyc777");
                UserFriendsDao userFriendsDao = new UserFriendsDao(friendsAdapter);
                userFriendsDao.addOneByUid(friend);
            }
        });

        return view;
    }

    private void showToast(String s) {
        Toast.makeText(view.getContext(), s,
                Toast.LENGTH_SHORT).show();
    }
}
