package com.example.bookreaderapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreaderapp.R
import com.example.bookreaderapp.UploadBookActivity
import com.example.bookreaderapp.adapters.BookListAdapter
import com.example.bookreaderapp.data.File
import com.example.bookreaderapp.viewmodel.BookListViewModel

class BookListActivity : AppCompatActivity(), BookListAdapter.OnItemClickListener {

    private lateinit var bookListAdapter: BookListAdapter
    private lateinit var recyclerView: RecyclerView
    private val bookListViewModel: BookListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_list)

        recyclerView = findViewById(R.id.book_list_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bookListAdapter = BookListAdapter(emptyList(), this)
        recyclerView.adapter = bookListAdapter

        bookListViewModel.allBooks.observe(this, Observer { books ->
            books?.let { bookListAdapter.updateBooks(it) }
        })

        val uploadBookButton = findViewById<Button>(R.id.upload_book_button)

        // Get the role from the Intent
        val role = intent.getStringExtra("USER_ROLE")
        Log.d("BookListActivity", "Role received: $role")
        if (role != null) {
            if (role.equals("NORMAL_USER", ignoreCase = true)) {
                Log.d("BookListActivity", "Hiding upload button for NORMAL_USER")
                uploadBookButton.visibility = View.GONE
            } else {
                Log.d("BookListActivity", "Role is $role, upload button remains visible")
            }
        } else {
            Log.d("BookListActivity", "No role received, upload button remains visible")
        }

        uploadBookButton.setOnClickListener {
            val intent = Intent(this, UploadBookActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(file: File) {
        bookListViewModel.downloadFile(this, file)
    }
}
