package com.example.grafatabata.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Tabata.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

        public abstract TabataDao tabataDao();

    }

