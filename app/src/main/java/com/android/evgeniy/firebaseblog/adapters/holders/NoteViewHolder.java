package com.android.evgeniy.firebaseblog.adapters.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.adapters.touchHelpers.ItemTouchHelperViewHolder;

public class NoteViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    private TextView note;
    private TextView date;
    private TextView time;
    private TextView email;
    private TextView name;
    private CardView cardView;

    public NoteViewHolder(View itemView) {
        super(itemView);

        date = itemView.findViewById(R.id.date);
        note = itemView.findViewById(R.id.note);
        time = itemView.findViewById(R.id.time);
        email = itemView.findViewById(R.id.email);
        name = itemView.findViewById(R.id.namesurname_tv);
        cardView = itemView.findViewById(R.id.card_view);
    }

    public CardView getCardView() {
        return cardView;
    }

    @Override
    public void onItemSelected() {
        cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.colorMyNoteSelected));
    }

    @Override
    public void onItemClear() {
        cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.colorMyNote));
    }

    public TextView getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note.setText(note);
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time.setText(time);
    }

    public TextView getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public TextView getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }
}
