package com.android.evgeniy.firebaseblog.adapters.touchHelpers;

public interface ItemTouchHelperAdapter {


    void onItemMove(int fromPosition, int toPosition);


    void onItemDismiss(int position);
}
