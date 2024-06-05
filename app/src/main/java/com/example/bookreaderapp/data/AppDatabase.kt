package com.example.bookreaderapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

// Add all your entities to the entities array
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
