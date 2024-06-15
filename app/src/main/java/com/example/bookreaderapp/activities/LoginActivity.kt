package com.example.bookreaderapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.bookreaderapp.R
import com.example.bookreaderapp.data.AppDatabase
import com.example.bookreaderapp.data.UserDao

class LoginActivity : AppCompatActivity() {

    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize the database and UserDao
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).allowMainThreadQueries().build()
        userDao = db.userDao()

        val loginButton = findViewById<Button>(R.id.login_btn)
        loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val username = findViewById<EditText>(R.id.username).text.toString().trim()
        val password = findViewById<EditText>(R.id.password).text.toString().trim()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            val user = userDao.login(username, password)
            if (user != null) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                // Navigate to the main/home screen of the app
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
        }
    }
}
