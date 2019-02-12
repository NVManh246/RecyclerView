package com.rikkei.recyclerview;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {
    @Query("SELECT * FROM contacts ORDER BY id DESC ")
    List<Contact> getAllContact();

    @Insert
    void insertContact(Contact... contacts);

    @Delete
    void deleteContact(Contact contact);

    @Update
    void updateContact(Contact... contacts);
}
