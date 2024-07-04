package com.example.bookreaderapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreaderapp.R
import com.example.bookreaderapp.UploadBookActivity
import com.example.bookreaderapp.adapters.BookListAdapter
import com.example.bookreaderapp.viewmodel.BookListViewModel

class BookListActivity : AppCompatActivity() {

    private lateinit var bookListAdapter: BookListAdapter
    private lateinit var recyclerView: RecyclerView
    private val bookListViewModel: BookListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_list)

        recyclerView = findViewById(R.id.book_list_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bookListAdapter = BookListAdapter(emptyList())
        recyclerView.adapter = bookListAdapter

        bookListViewModel.allBooks.observe(this, Observer { books ->
            books?.let { bookListAdapter.updateBooks(it) }
        })

        val uploadBookButton = findViewById<Button>(R.id.upload_book_button)
        uploadBookButton.setOnClickListener {
            val intent = Intent(this, UploadBookActivity::class.java)
            startActivity(intent)
        }
    }
}
