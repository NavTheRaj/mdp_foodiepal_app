package com.example.foodiepal.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.foodiepal.databinding.ActivityLoginBinding
import java.util.Timer
import kotlin.concurrent.timerTask

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val userCredentials = mutableListOf<Map<String, String>>(
        mapOf<String, String>("username" to "admin", "password" to "admin")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE)

        val username = binding.editTextUsername
        val password = binding.editTextPassword

        val login = binding.buttonLogin
        val loading = binding.loading

        if (isLoggedIn()) {
            redirectToLandingPage()
        }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            if (username.text.toString() == "" || password.text.toString() == "") {
                Toast.makeText(
                    this,
                    "Username and Password are required fields.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                var loginObj = userCredentials.find { cred ->
                    cred["username"] == username.text.toString() && cred["password"] == password.text.toString()
                }
                if (loginObj != null) {
                    handleLogin(username.text.toString(), username.text.toString())
                } else {
                    Toast.makeText(
                        this,
                        "Username or Password is invalid. Please try again!",
                        Toast.LENGTH_SHORT
                    ).show()
                    loading.visibility = View.GONE
                }
            }
        }
    }

    private fun handleLogin(username: String, password: String) {
        Timer().schedule(timerTask {
            saveCredentials(username)
            redirectToLandingPage()
        }, 2000)
    }

    private fun saveCredentials(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()
    }

    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getString("username", null) != null
    }

    private fun redirectToLandingPage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}