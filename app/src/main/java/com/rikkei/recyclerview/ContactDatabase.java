package com.rikkei.recyclerview;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    private static ContactDatabase sContactDatabase;

    public static final String DATABASE_NAME = "contact-database";

    public abstract ContactDAO contactDAO();

    public static ContactDatabase getInstance(Context context) {
        if(sContactDatabase == null) {
            sContactDatabase = Room.databaseBuilder(context, ContactDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return sContactDatabase;
    }
}
