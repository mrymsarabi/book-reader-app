package com.example.bookreaderapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.bookreaderapp.data.AppDatabase
import com.example.bookreaderapp.data.File

class BookListViewModel(application: Application) : AndroidViewModel(application) {
    private val fileDao = AppDatabase.getDatabase(application).fileDao()
    val allBooks: LiveData<List<File>> = fileDao.getAllBooks()
}
