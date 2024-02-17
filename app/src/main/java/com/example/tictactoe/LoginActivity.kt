package com.example.tictactoe

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var login: Button
    private lateinit var register: Button
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginPassword = findViewById(R.id.loginPassword)
        loginEmail = findViewById(R.id.loginEmail)
        login = findViewById(R.id.login)
        register = findViewById(R.id.register)
        prefs = this.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        login.setOnClickListener {
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()
            val helper = DbHelper(this)
            val res = helper.getUserInfo(email, password)

            if (res == null) {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val editor = prefs.edit()
            editor.putLong("userId", res.first)
            editor.putInt("highScore", res.second)
            editor.apply()

            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        register.setOnClickListener {
            intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}