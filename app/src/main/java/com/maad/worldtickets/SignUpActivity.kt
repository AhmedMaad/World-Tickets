package com.maad.worldtickets

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.worldtickets.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    var isPasswordVisible = false
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var name = ""
    private var email = ""
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        db = Firebase.firestore

        binding.passwordEt.setDrawableRightTouch {

            if (!isPasswordVisible) {
                binding.passwordEt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.passwordEt.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_visibility_on,
                    0
                )
                isPasswordVisible = true
                binding.passwordEt.setSelection(binding.passwordEt.length())
            } else {
                binding.passwordEt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passwordEt.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_visibility_off,
                    0
                )
                isPasswordVisible = false
                binding.passwordEt.setSelection(binding.passwordEt.length())
            }

        }

        binding.loginTv.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        binding.registerBtn.setOnClickListener {
            email = binding.emailEt.text.toString()
            val pass = binding.passwordEt.text.toString()
            name = binding.nameEt.text.toString()
            if (email.isEmpty() || pass.isEmpty() || name.isEmpty()) {
                if (email.isEmpty())
                    binding.emailEt.error = "Email is required"
                if (pass.isEmpty())
                    binding.passwordEt.error = "Password is required"
                if (name.isEmpty())
                    binding.nameEt.error = "Name is required"
            } else {
                binding.progress.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful)
                            sendVerificationLink()
                    }
            }

        }

    }

    private fun sendVerificationLink() {
        val user = Firebase.auth.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    addUser()
            }
    }

    private fun addUser() {
        val userId = Firebase.auth.currentUser!!.uid
        val user = User(userId, name, email)
        db.collection("users").document(userId).set(user).addOnSuccessListener {
            val prefs = getSharedPreferences("settings", MODE_PRIVATE).edit()
            prefs.putString("id", userId)
            prefs.apply()
            binding.progress.visibility = View.VISIBLE
            Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun EditText.setDrawableRightTouch(setClickListener: () -> Unit) {
        this.setOnTouchListener(View.OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= this.right - this.compoundDrawables[2].bounds.width()
                ) {
                    setClickListener()
                    return@OnTouchListener true
                }
            }
            false
        })
    }

}