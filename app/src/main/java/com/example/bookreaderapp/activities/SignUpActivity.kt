package com.example.bookreaderapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.bookreaderapp.R
import com.example.bookreaderapp.data.AppDatabase
import com.example.bookreaderapp.data.User
import com.example.bookreaderapp.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {

    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize the database and UserDao with Room
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()
        userDao = db.userDao()

        val signUpButton = findViewById<Button>(R.id.signup_btn)
        signUpButton.setOnClickListener {
            if (validateInput()) {
                checkAndInsertUser()
            } else {
                Toast.makeText(this, "Please fill out all fields correctly.", Toast.LENGTH_SHORT).show()
            }
        }

        // Set OnClickListener for the TextView to navigate to LoginActivity
        val loginTextView = findViewById<TextView>(R.id.signup_end_text)
        loginTextView.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun validateInput(): Boolean {
        val firstName = findViewById<EditText>(R.id.first_name).text.toString().trim()
        val lastName = findViewById<EditText>(R.id.last_name).text.toString().trim()
        val username = findViewById<EditText>(R.id.username).text.toString().trim()
        val password = findViewById<EditText>(R.id.password).text.toString().trim()
        val userTypeRadioGroup = findViewById<RadioGroup>(R.id.user_type_radio_group)

        return firstName.isNotEmpty() && lastName.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() &&
                userTypeRadioGroup.checkedRadioButtonId != -1
    }

    private fun checkAndInsertUser() {
        val firstName = findViewById<EditText>(R.id.first_name).text.toString().trim()
        val lastName = findViewById<EditText>(R.id.last_name).text.toString().trim()
        val username = findViewById<EditText>(R.id.username).text.toString().trim()
        val password = findViewById<EditText>(R.id.password).text.toString().trim()

        // Retrieve the selected user type from the RadioGroup
        val userTypeRadioGroup = findViewById<RadioGroup>(R.id.user_type_radio_group)
        val selectedUserTypeId = userTypeRadioGroup.checkedRadioButtonId
        val userType = if (selectedUserTypeId == R.id.admin) "ADMIN" else "NORMAL_USER"

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val existingUser = userDao.checkUserExists(username)
                if (existingUser != null) {
                    withContext(Dispatchers.Main) {
                        showToast("Username already exists. Please choose a different username.")
                    }
                } else {
                    val user = User(firstName = firstName, lastName = lastName, username = username, password = password, role = userType)
                    try {
                        userDao.insert(user)
                        withContext(Dispatchers.Main) {
                            showToast("User registered successfully.")
                            navigateToLogin()
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SignUpActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@SignUpActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}
