package com.android.evgeniy.firebaseblog.dataaccess.api;

import java.util.ArrayList;

public abstract class Container<T> {
    private ArrayList<T> list;

    public Container() {
        this.list = new ArrayList<>();
    }

    public ArrayList<T> getAll() {
        return list;
    }

    public void add(T item) {
        list.add(item);
    }

    public int set(int index, T newItem) {
        list.set(index, newItem);
        return index;
    }

    public int delete(int index) {
        list.remove(index);
        return index;
    }

    public int size() {
        return list.size();
    }

    public T get(int index) {
        return list.get(index);
    }
}
