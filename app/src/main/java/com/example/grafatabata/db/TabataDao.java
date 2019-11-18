package com.example.grafatabata.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TabataDao {
    @Query("SELECT * FROM tabata")
    List<Tabata> getAll();

    @Insert
    void insert(Tabata tabata);

    @Delete
    void delete(Tabata tabata);

    @Update
    void update(Tabata tabata);
}
