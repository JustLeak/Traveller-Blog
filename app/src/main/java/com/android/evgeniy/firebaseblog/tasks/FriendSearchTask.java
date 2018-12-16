package com.android.evgeniy.firebaseblog.tasks;

import android.os.AsyncTask;

import com.android.evgeniy.firebaseblog.dataaccess.UserFriendsDao;
import com.android.evgeniy.firebaseblog.models.Friend;
import com.android.evgeniy.firebaseblog.services.SearchMap;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

public class FriendSearchTask extends AsyncTask<DataSnapshot, Integer, String> {
    private SearchMap searchMap;
    private HashMap<String, String> map;
    private UserFriendsDao userFriendsDao;
    private String userId;
    private String email;

    public FriendSearchTask(SearchMap searchMap, String userId, String email) {
        this.searchMap = searchMap;
        this.userId = userId;
        this.email = email;
        map = new HashMap<>();
        userFriendsDao = new UserFriendsDao(userId);
    }

    @Override
    protected String doInBackground(DataSnapshot... dataSnapshots) {
        Friend friend;
        for (DataSnapshot dataS : dataSnapshots[0].getChildren()) {
            friend = dataS.getValue(Friend.class);
            map.put(friend.getEmail(), friend.getId());
        }

        if (map.containsKey(email)) {
            return email.concat(" is already on your friend list.");
        } else if (map.get(email) != null) {
            Friend newFriend = new Friend();
            newFriend.setEmail(email);
            newFriend.setId(map.get(email));
            userFriendsDao.addOneByUid(newFriend, userId);
            return "Added to your friends";
        } else return "User is not found";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        searchMap.showToast(result);
    }
}
