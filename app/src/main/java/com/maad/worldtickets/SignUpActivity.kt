package com.maad.worldtickets

import android.R
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.maad.worldtickets.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*etPassword.setOnTouchListener(object : OnTouchListener() {
            fun onTouch(view: View?, event: MotionEvent): Boolean {
                //final int DRAWABLE_LEFT = 0;
                //final int DRAWABLE_TOP = 1;
                val DRAWABLE_RIGHT = 2
                //final int DRAWABLE_BOTTOM = 3;
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= etPassword.getRight() - etPassword.getCompoundDrawables()
                            .get(DRAWABLE_RIGHT).getBounds().width()
                    ) {
                        // your action here
                        if (counter % 2 === 0) {
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(
                                0,
                                0,
                                R.drawable.ic_visibility_black_24dp,
                                0
                            )
                            etPassword.setTransformationMethod(null) //shows the password
                        } else {
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(
                                0,
                                0,
                                R.drawable.ic_visibility_off_black_24dp,
                                0
                            )
                            etPassword.setTransformationMethod(PasswordTransformationMethod()) //hides the password
                        }
                        ++counter
                        return true
                    }
                }
                return false
            }
        })*/

    }

}