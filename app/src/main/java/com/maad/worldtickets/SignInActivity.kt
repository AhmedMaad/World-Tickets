package com.maad.worldtickets

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maad.worldtickets.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.signUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.login.setOnClickListener {
            //val email = binding.emailEt.text.toString()
            //val password = binding.passwordEt.text.toString()
            val email = "atef.ahmed1341@gmail.com"
            val password = "123456"
            if (email.isEmpty() || password.isEmpty())
                Toast.makeText(this, "Fill the fields before login", Toast.LENGTH_SHORT).show()
            else {
                binding.progress.visibility = View.VISIBLE
                val auth = Firebase.auth
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful && !task.result.user!!.isEmailVerified){
                            Toast.makeText(this, "Verify your email, then login", Toast.LENGTH_SHORT).show()
                            binding.progress.visibility = View.INVISIBLE
                        }
                        else if (task.isSuccessful) {
                            binding.progress.visibility = View.INVISIBLE
                            Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT)
                                .show()
                            val editor = getSharedPreferences("settings", MODE_PRIVATE).edit()
                            editor.putString("id", task.result.user!!.getUid())
                            editor.apply()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            binding.progress.visibility = View.INVISIBLE
                            Toast.makeText(
                                this, "Error, check your connection/password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
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