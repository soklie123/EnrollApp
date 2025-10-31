package com.example.student_enrollment_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class activity_sign_up : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    private lateinit var etFullName: EditText;
    private lateinit var etEmailSignUp: EditText;
    private lateinit var etPasswordSignUp: EditText;
    private lateinit var etConfirmPassword: EditText;
    private lateinit var btnSignUp: Button;
    private lateinit var tvSignInRedirect: TextView;
    private lateinit var progressBarSignUp: ProgressBar;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        auth= Firebase.auth
        initializeViews();
        setupClickListener();

    }
    private fun initializeViews(){
        etFullName=findViewById(R.id.etFullName);
        etEmailSignUp=findViewById(R.id.etEmailSignUp);
        etPasswordSignUp=findViewById(R.id.etPasswordSignUp);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);
        btnSignUp=findViewById(R.id.btnSignUp);
        tvSignInRedirect=findViewById(R.id.tvSignInRedirect);
        progressBarSignUp=findViewById(R.id.progressBarSignUp);
    }
    private fun  setupClickListener(){
        btnSignUp.setOnClickListener {
            var fullName=etFullName.text.toString().trim();
            var email=etEmailSignUp.text.toString().trim();
            var password=etPasswordSignUp.text.toString().trim();
            var confirmPassword=etConfirmPassword.text.toString().trim();
            if(validateInput(fullName,email,password,confirmPassword)){
               SignUpWithEmail(email,password);
            }
        }
        tvSignInRedirect.setOnClickListener {
            finish() // Go back to SignInActivity
        }

    }
    private fun validateInput(fullName: String,email: String,password: String,confirmPassword: String): Boolean{
        if(fullName.isEmpty()){
            etFullName.error="FullName is required";
            return false;
        }
        if(email.isEmpty()){
            etEmailSignUp.error="Email is required";
            return false;
        }
        if(password.isEmpty()){
            etPasswordSignUp.error="Password id required";
            return false;
        }
        if(password.length<6){
            etPasswordSignUp.error="Password must me at least 6 charachers"
            return false;
        }
        if(confirmPassword.isEmpty()){
            etConfirmPassword.error="Please confirm your password";
            return false;
        }
        if(password !=confirmPassword){
            etConfirmPassword.error="Passwords do not much";
            return false;
        }
        return true;
    }
    private fun SignUpWithEmail(email: String,password: String){
        showLoading(true);
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
            task->showLoading(false)
            if(task.isSuccessful){
                Toast.makeText(this,"Account created Sucessfully!", Toast.LENGTH_SHORT).show();
                startActivity(Intent(this, MainActivity::class.java));
            }else{
                Toast.makeText(this,"Sign Up failed :${task.exception?.message}",Toast.LENGTH_SHORT).show()

            }
        }

    }
    private fun showLoading(show: Boolean){
        progressBarSignUp.visibility=if(show) android.view.View.VISIBLE else android.view.View.GONE
        btnSignUp.isEnabled=!show
        tvSignInRedirect.isEnabled=!show


    }

}