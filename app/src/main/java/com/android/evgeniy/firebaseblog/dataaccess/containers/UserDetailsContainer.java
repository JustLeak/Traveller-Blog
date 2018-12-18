package com.android.evgeniy.firebaseblog.dataaccess.containers;

import com.android.evgeniy.firebaseblog.models.UserDetails;

import java.util.ArrayList;

public final class UserDetailsContainer extends ArrayList<UserDetails> {

    public void addOrReplace(UserDetails newUserDetails) {
        int index = findIndexByEmail(newUserDetails.getEmail());
        if (index != -1)
            super.set(index, newUserDetails);
        else super.add(newUserDetails);
    }

    private int findIndexByEmail(String email) {
        for (int i = 0; i < super.size(); i++) {
            if (super.get(i).getEmail().equals(email)) {
                return i;
            }
        }
        return -1;
    }

    public String getNameByEmail(String email) {
        int index = findIndexByEmail(email);

        if (index != -1) {
            UserDetails details = super.get(findIndexByEmail(email));
            return details.getLastName() + " " + details.getFirstName();
        }

        return "СКАЖИ ФРАЕРУ ЧТО ТУТ ОШИБКА";
    }
}
