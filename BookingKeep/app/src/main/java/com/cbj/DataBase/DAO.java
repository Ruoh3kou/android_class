package com.cbj.DataBase;

import java.util.List;

public interface DAO<T> {
    List<T> queryAll();

    List<T> queryAction(String selection,
                        String[] selectionArgs);

    void delete(T t);

    void insert(T t);

    void update(T t);
}