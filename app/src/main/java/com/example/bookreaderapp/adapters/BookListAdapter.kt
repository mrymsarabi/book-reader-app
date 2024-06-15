// File: app/src/main/java/com/example/bookreaderapp/adapters/BookListAdapter.kt

package com.example.bookreaderapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreaderapp.R
import com.example.bookreaderapp.data.File

class BookListAdapter(private val books: List<File>) : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.book_title)
        val authorTextView: TextView = itemView.findViewById(R.id.book_author)
        val descriptionTextView: TextView = itemView.findViewById(R.id.book_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_book_item, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.titleTextView.text = book.title
        holder.authorTextView.text = book.author
        holder.descriptionTextView.text = book.description
    }

    override fun getItemCount() = books.size
}
