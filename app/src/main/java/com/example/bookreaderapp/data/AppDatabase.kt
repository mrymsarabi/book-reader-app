package com.example.bookreaderapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, File::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun fileDao(): FileDao
}
