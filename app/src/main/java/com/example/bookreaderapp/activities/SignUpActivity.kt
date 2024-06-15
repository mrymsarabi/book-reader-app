package com.example.bookreaderapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.bookreaderapp.R
import com.example.bookreaderapp.data.AppDatabase
import com.example.bookreaderapp.data.User
import com.example.bookreaderapp.data.UserDao

class SignUpActivity : AppCompatActivity() {

    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize the database and UserDao
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).allowMainThreadQueries().build()
        userDao = db.userDao()

        val signUpButton = findViewById<Button>(R.id.signup_btn)
        signUpButton.setOnClickListener {
            if (validateInput()) {
                insertUserIntoDatabase()
            } else {
                Toast.makeText(this, "Please fill out all fields correctly.", Toast.LENGTH_SHORT).show()
            }
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

    private fun insertUserIntoDatabase() {
        val firstName = findViewById<EditText>(R.id.first_name).text.toString().trim()
        val lastName = findViewById<EditText>(R.id.last_name).text.toString().trim()
        val username = findViewById<EditText>(R.id.username).text.toString().trim()
        val password = findViewById<EditText>(R.id.password).text.toString().trim()

        // Retrieve the selected user type from the RadioGroup
        val userTypeRadioGroup = findViewById<RadioGroup>(R.id.user_type_radio_group)
        val selectedUserTypeId = userTypeRadioGroup.checkedRadioButtonId
        val userType = if (selectedUserTypeId == R.id.admin) "ADMIN" else "NORMAL_USER"

        val user = User(firstName = firstName, lastName = lastName, username = username, password = password, role = userType)
        userDao.insert(user)

        // Verify insertion by querying the database
        val insertedUser = userDao.checkUserExists(username)
        if (insertedUser != null) {
            Toast.makeText(this, "User registered successfully.", Toast.LENGTH_SHORT).show()
            // Navigate to LoginActivity after successful registration
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Failed to register user.", Toast.LENGTH_SHORT).show()
        }
    }
}
