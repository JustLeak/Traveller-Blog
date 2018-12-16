package com.android.evgeniy.firebaseblog.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;

public class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    private TextView email;

    public FriendViewHolder(View itemView) {
        super(itemView);
        email = itemView.findViewById(R.id.email);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, R.id.context_menu_item_notes, Menu.NONE, "Notes");
        menu.add(Menu.NONE, R.id.context_menu_item_profile, Menu.NONE, "Profile");
        menu.add(Menu.NONE, R.id.context_menu_item_notes_on_map, Menu.NONE, "Notes on map");
    }
}
