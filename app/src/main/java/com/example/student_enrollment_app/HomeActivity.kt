package com.example.student_enrollment_app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        // Check if user is authenticated
        checkAuthentication()

        // Set up click listeners for toolbar icons
        setupToolbarIcons()

        // Update welcome text with user name
        updateWelcomeText()
    }

    private fun checkAuthentication() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // User is not authenticated, redirect to SignIn
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    private fun setupToolbarIcons() {
        // Notification icon click
        findViewById<ImageView>(R.id.iconNotification).setOnClickListener {
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show()
            // Add your notification logic here
        }

        // Profile icon click
        findViewById<ShapeableImageView>(R.id.iconProfile).setOnClickListener {
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            // Navigate to profile or show user info
        }
    }

    private fun updateWelcomeText() {
        val currentUser = auth.currentUser
        val welcomeText = findViewById<TextView>(R.id.welcome_text)

        if (currentUser != null) {
            // Show user's name or email
            val userName = currentUser.displayName ?: currentUser.email ?: "Student"
            welcomeText.text = "Welcome, $userName!"
        } else {
            welcomeText.text = "Welcome!"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                signOut()
                true
            }
            R.id.action_profile -> {
                // Handle profile menu item click
                Toast.makeText(this, "Profile menu clicked", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        auth.signOut()
        Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        // Prevent going back to sign in/sign up activities
        moveTaskToBack(true)
    }
}