// FileDao.kt
package com.example.bookreaderapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FileDao {
    @Insert
    fun insert(file: File)

    @Query("SELECT * FROM files")
    fun getAllBooks(): LiveData<List<File>>

    @Query("SELECT * FROM files WHERE id = :id")
    fun getBookById(id: Int): File?
}
