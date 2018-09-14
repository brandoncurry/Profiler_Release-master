package com.brandoncurry.profiler.utils;

import com.brandoncurry.profiler.Profiler;
import com.google.firebase.database.DatabaseReference;

public class DatabaseManager {

    public static DatabaseManager databaseManager;

    public DatabaseManager(){
        databaseManager = new DatabaseManager();
    }

    public static DatabaseManager getInstance(){
        return databaseManager;
    }

    public static DatabaseReference getDatabase(){
        return Profiler.globalDatabase.getReference();
    }
}
