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
import com.android.evgeniy.firebaseblog.adapters.ClickFriendRecyclerAdapter;
import com.android.evgeniy.firebaseblog.models.Friend;
import com.android.evgeniy.firebaseblog.repositories.UserFriendsDao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FriendsFragment extends Fragment implements ClickFriendRecyclerAdapter.OnItemClickListener{
    private View view;
    private RecyclerView friendsList;
    private Button button;

    private UserFriendsDao userFriendsDao;
    private ClickFriendRecyclerAdapter clickFriendRecyclerAdapter;

    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends, container, false);
        button = view.findViewById(R.id.add_friend_btn);
        friendsList = view.findViewById(R.id.friends);

        friendsList.setAdapter(clickFriendRecyclerAdapter);
        friendsList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        friendsList.setHasFixedSize(false);

        userFriendsDao.getAll(clickFriendRecyclerAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend friend = new Friend();
                friend.setEmail("nikitamatusevich@mail.ru");
                friend.setId("matyc777");
                userFriendsDao.addOneByUid(friend, user.getUid());
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userFriendsDao = new UserFriendsDao(user.getUid());
        clickFriendRecyclerAdapter = new ClickFriendRecyclerAdapter(getLayoutInflater(), this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Friend friend = clickFriendRecyclerAdapter.getFriendByIndex(position);
        Toast.makeText(view.getContext(), friend.getEmail() + " " + friend.getId(), Toast.LENGTH_SHORT).show();

    }
}
