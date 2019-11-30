package com.example.grafatabata.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TabataDao {
    @Query("SELECT * FROM tabata ORDER BY id DESC")
    List<Tabata> getAll();

    @Insert
    void insert(Tabata tabata);

    @Query("DELETE FROM tabata WHERE id = :id")
    void delete(long id);

    @Update
    void update(Tabata tabata);

    @Query("SELECT * FROM tabata WHERE id = :id")
    Tabata findById(long id);


}