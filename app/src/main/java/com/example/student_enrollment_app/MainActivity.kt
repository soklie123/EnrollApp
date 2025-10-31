package com.example.student_enrollment_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Check if user is logged in, if not redirect to SignIn
        if (auth.currentUser == null) {
            redirectToSignIn()
            return
        }

        setupViews()
    }

    private fun setupViews() {
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)

        // Display welcome message with user email
        val currentUser = auth.currentUser
        currentUser?.email?.let { email ->
            tvWelcome.text = "Welcome, $email!"
        }

        btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        auth.signOut() // logout from Firebase
        redirectToSignIn()
    }

    private fun redirectToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish() // Close MainActivity so user can't go back with back button
    }

    override fun onStart() {
        super.onStart()
        // Double check authentication state when activity starts
        if (auth.currentUser == null) {
            redirectToSignIn()
        }
    }
}