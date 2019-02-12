package com.rikkei.recyclerview;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "contacts")
public class Contact {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "number_phone")
    private String numberPhone;

    public Contact() {
    }

    @Ignore
    public Contact(String name, String numberPhone) {
        this.name = name;
        this.numberPhone = numberPhone;
    }

    public String getName() {
        return name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
