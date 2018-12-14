package com.android.evgeniy.firebaseblog.tasks;

import android.os.AsyncTask;

import com.android.evgeniy.firebaseblog.models.Friend;
import com.android.evgeniy.firebaseblog.dataaccess.UserFriendsDao;
import com.android.evgeniy.firebaseblog.services.SearchMap;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

public class FriendSearchTask extends AsyncTask<DataSnapshot, Integer, Boolean> {
    private SearchMap searchMap;
    private HashMap<String, String> map;
    private UserFriendsDao userFriendsDao;
    private String userId;
    private String email;

    public FriendSearchTask(SearchMap searchMap, String userId, String email){
        this.searchMap = searchMap;
        this.userId = userId;
        this.email = email;
        map = new HashMap<>();
        userFriendsDao = new UserFriendsDao(userId);
    }

    @Override
    protected Boolean doInBackground(DataSnapshot... dataSnapshots) {
        Friend friend;
        for (DataSnapshot dataS : dataSnapshots[0].getChildren()) {
            friend = dataS.getValue(Friend.class);
            map.put(friend.getEmail(), friend.getId());
        }
        if (map.get(email) != null) {
            Friend newFriend = new Friend();
            newFriend.setEmail(email);
            newFriend.setId(map.get(email));
            userFriendsDao.addOneByUid(newFriend, userId);
            return true;
        } else return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        searchMap.showResult(result);
    }
}
