package com.example.bookreaderapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.bookreaderapp.R
import com.example.bookreaderapp.UploadBookActivity
import com.example.bookreaderapp.adapters.BookListAdapter
import com.example.bookreaderapp.data.AppDatabase
import com.example.bookreaderapp.data.File

class BookListActivity : AppCompatActivity() {

    private lateinit var bookListAdapter: BookListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_list)

        recyclerView = findViewById(R.id.book_list_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bookListAdapter = BookListAdapter(emptyList()) // Initially pass an empty list
        recyclerView.adapter = bookListAdapter

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .allowMainThreadQueries()
            .build()

        val fileDao = db.fileDao()
        val files: List<File> = fileDao.getAllBooks()
        bookListAdapter.updateBooks(files)

        val uploadBookButton = findViewById<Button>(R.id.upload_book_button)
        uploadBookButton.setOnClickListener {
            val intent = Intent(this, UploadBookActivity::class.java)
            startActivity(intent)
        }
    }
}
