// BookListViewModel.kt (Updated with download function)
package com.example.bookreaderapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.bookreaderapp.data.AppDatabase
import com.example.bookreaderapp.data.File
import com.example.bookreaderapp.util.FileDownloader

class BookListViewModel(application: Application) : AndroidViewModel(application) {
    private val fileDao = AppDatabase.getDatabase(application).fileDao()
    val allBooks: LiveData<List<File>> = fileDao.getAllBooks()

    fun downloadFile(context: Context, file: File) {
        // Assuming filePath is a valid URL or path accessible to DownloadManager
        val downloadId = FileDownloader.downloadFile(context, file.filePath, file.title)
        // You may want to save downloadId or handle completion in a BroadcastReceiver
    }
}
