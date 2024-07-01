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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_list)

        // Initialize Room database with migration
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2) // Add your migration object here
            .allowMainThreadQueries()
            .build()

        val fileDao = db.fileDao()
        val files: List<File> = fileDao.getAllBooks() // Fetch data from database

        val recyclerView = findViewById<RecyclerView>(R.id.book_list_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BookListAdapter(files) // Bind data to RecyclerView

        // Set up button click to navigate to UploadBookActivity
        val uploadBookButton = findViewById<Button>(R.id.upload_book_button)
        uploadBookButton.setOnClickListener {
            val intent = Intent(this, UploadBookActivity::class.java)
            startActivity(intent)
        }
    }
}
