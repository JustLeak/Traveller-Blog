package com.android.evgeniy.firebaseblog.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;

public class FriendViewHolder extends RecyclerView.ViewHolder {
    private TextView email;

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public FriendViewHolder(View itemView) {
        super(itemView);

        email = (TextView) itemView.findViewById(R.id.email);
    }
}
