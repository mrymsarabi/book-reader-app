// UploadActivity.kt

package com.example.bookreaderapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bookreaderapp.data.AppDatabase
import com.example.bookreaderapp.data.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UploadBookActivity : AppCompatActivity() {

    private lateinit var bookTitle: EditText
    private lateinit var bookAuthor: EditText
    private lateinit var bookDescription: EditText
    private lateinit var selectFileButton: Button
    private lateinit var uploadBookButton: Button
    private var selectedFileUri: Uri? = null

    private val fileDao by lazy {
        AppDatabase.getDatabase(this).fileDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_book)

        bookTitle = findViewById(R.id.book_title)
        bookAuthor = findViewById(R.id.book_author)
        bookDescription = findViewById(R.id.book_description)
        selectFileButton = findViewById(R.id.select_file_btn)
        uploadBookButton = findViewById(R.id.upload_book_btn)

        selectFileButton.setOnClickListener {
            selectFile()
        }

        uploadBookButton.setOnClickListener {
            uploadFile()
        }
    }

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/pdf"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            selectedFileUri = data?.data
            if (selectedFileUri != null) {
                Toast.makeText(this, "File Selected: $selectedFileUri", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadFile() {
        if (selectedFileUri == null) {
            Toast.makeText(this, "Please select a file first", Toast.LENGTH_SHORT).show()
            return
        }

        val title = bookTitle.text.toString()
        val author = bookAuthor.text.toString()
        val description = bookDescription.text.toString()

        if (title.isEmpty() || author.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val filePath = getFilePath(selectedFileUri!!)
        saveFileInfoToDatabase(title, author, description, filePath)
        Toast.makeText(this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show()
    }

    private fun getFilePath(uri: Uri): String {
        var filePath: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor.use { cursor ->
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (columnIndex != -1) {
                    filePath = cursor.getString(columnIndex)
                }
            }
        }
        return filePath ?: uri.path ?: ""
    }

    private fun saveFileInfoToDatabase(title: String, author: String, description: String, filePath: String) {
        val file = File(
            title = title,
            author = author,
            description = description,
            filePath = filePath
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                fileDao.insert(file)
            }
        }
    }
}
