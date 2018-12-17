package com.android.evgeniy.firebaseblog.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.adapters.ClickFriendRecyclerAdapter;
import com.android.evgeniy.firebaseblog.dataaccess.UserFriendsDao;
import com.android.evgeniy.firebaseblog.services.SearchMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FriendsFragment extends Fragment implements ClickFriendRecyclerAdapter.OnItemClickListener,
        ClickFriendRecyclerAdapter.OnItemLongClickListener {
    private View view;
    private RecyclerView friendsList;
    private SearchView searchView;

    private UserFriendsDao userFriendsDao;
    private ClickFriendRecyclerAdapter clickFriendRecyclerAdapter;
    private int position;
    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends, container, false);
        friendsList = view.findViewById(R.id.friends);
        searchView = view.findViewById(R.id.searchView);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.friends);

        clickFriendRecyclerAdapter = new ClickFriendRecyclerAdapter(getLayoutInflater(), this, this);
        friendsList.setAdapter(clickFriendRecyclerAdapter);
        friendsList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        friendsList.setHasFixedSize(false);
        registerForContextMenu(friendsList);

        userFriendsDao.getAll(clickFriendRecyclerAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchMap searchMap = new SearchMap(user.getUid());
                searchMap.findFriendByEmail(query.trim().toLowerCase(), view.getContext());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userFriendsDao = new UserFriendsDao(user.getUid());
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle arguments = new Bundle();
        arguments.putString("userId", clickFriendRecyclerAdapter.getFriends().get(position).getId());
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(arguments);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Bundle arguments = new Bundle();
        arguments.putString("userId", clickFriendRecyclerAdapter.getFriends().get(position).getId());
        switch (item.getItemId()) {
            case R.id.context_menu_item_notes:
                NotesFragment notesFragment = new NotesFragment();
                notesFragment.setArguments(arguments);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, notesFragment).addToBackStack(null).commit();
                return true;
            case R.id.context_menu_item_profile:
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setArguments(arguments);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).addToBackStack(null).commit();
                return true;
            case R.id.context_menu_item_notes_on_map:
                MapFragment mapFragment = new MapFragment();
                mapFragment.setArguments(arguments);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, mapFragment).addToBackStack(null).commit();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        this.position = position;
        return false;
    }
}
