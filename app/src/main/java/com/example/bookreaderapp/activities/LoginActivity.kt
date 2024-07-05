package com.example.bookreaderapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.bookreaderapp.R
import com.example.bookreaderapp.data.AppDatabase
import com.example.bookreaderapp.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize the database and UserDao with migration
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).addMigrations(AppDatabase.MIGRATION_1_2)
            .build()

        userDao = db.userDao()

        val loginButton = findViewById<Button>(R.id.login_btn)
        loginButton.setOnClickListener {
            loginUser()
        }

        // Set OnClickListener for the TextView to navigate to SignUpActivity
        val signUpTextView = findViewById<TextView>(R.id.login_end_text)
        signUpTextView.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun loginUser() {
        val username = findViewById<EditText>(R.id.username).text.toString().trim()
        val password = findViewById<EditText>(R.id.password).text.toString().trim()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val user = userDao.login(username, password)
                    if (user != null) {
                        showToast("Login successful!")
                        navigateToBookList()
                    } else {
                        // More descriptive message for invalid credentials
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "Invalid login credentials.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } else {
            showToast("Please enter both username and password")
        }
    }

    private fun showToast(message: String) {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToBookList() {
        val intent = Intent(this, BookListActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}
