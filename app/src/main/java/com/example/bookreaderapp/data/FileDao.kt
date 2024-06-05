// FileDao.kt
package com.example.bookreaderapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FileDao {
    @Insert
    suspend fun insertFile(file: File)

    @Query("SELECT * FROM files")
    suspend fun getAllFiles(): List<File>
}
