package com.example.bookreaderapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration

@Database(entities = [User::class, File::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun fileDao(): FileDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE files ADD COLUMN title TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE files ADD COLUMN author TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE files ADD COLUMN description TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}
