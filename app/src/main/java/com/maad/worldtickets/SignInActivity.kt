package com.maad.worldtickets

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
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