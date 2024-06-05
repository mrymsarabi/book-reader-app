package com.example.bookreaderapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "files")
data class File(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "file_name")
    val fileName: String,

    @ColumnInfo(name = "file_path")
    val filePath: String
) {
    // Dummy primary constructor parameter
    constructor(dummy: Unit = Unit) : this(0, "", "")
}
