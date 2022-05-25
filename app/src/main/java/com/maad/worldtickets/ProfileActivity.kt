package com.maad.worldtickets

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.maad.worldtickets.databinding.ActivityProfileBinding
import com.maad.worldtickets.databinding.ActivitySignUpBinding
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var user: User
    private var imageUri: Uri? = null
    private lateinit var binding: ActivityProfileBinding
    private var updatedName = ""
    private var updatedPassword = ""
    private var updatedEmail = ""
    private var facebookLink = ""
    private var phoneNo = ""
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore

        binding.backIv.setOnClickListener { finish() }

        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        id = prefs.getString("id", null)!!
        db.collection("users").document(id).get().addOnSuccessListener {
            user = it.toObject(User::class.java)!!
            if (user.pp.isEmpty())
                binding.ppIv.setImageResource(R.drawable.ic_person)
            else
                Glide.with(this).load(user.pp).into(binding.ppIv)

            binding.nameEt.setText(user.name)
            binding.phoneEt.setText(user.phoneNo)
            binding.emailEt.setText(user.email)
            binding.passwordEt.setText(user.password)
            binding.facebookEt.setText(user.facebookLink)
            binding.progress.visibility = View.INVISIBLE
        }

        binding.discardBtn.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder
                .setIcon(R.drawable.ic_discard)
                .setTitle("Discard Changes?")
                .setMessage("Are you sure you want to discard changes?")
                .setPositiveButton("yes") { _, _ -> finish() }
                .setNegativeButton("no") { dialog, _ -> dialog.dismiss() }
                .setCancelable(false)
            val alert = builder.show()
            alert.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.gold))
            alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.gold))
        }

        binding.saveBtn.setOnClickListener {

            updatedName = binding.nameEt.text.toString()
            updatedPassword = binding.passwordEt.text.toString()
            updatedEmail = binding.emailEt.text.toString()
            facebookLink = binding.facebookEt.text.toString()
            phoneNo = binding.phoneEt.text.toString()

            if (updatedName.isEmpty())
                binding.nameEt.error = "Name is required"
            else if (updatedPassword.isEmpty() || updatedPassword.length < 6)
                binding.passwordEt.error = "Password should be > 6 letters"
            else if (updatedEmail.isEmpty())
                binding.emailEt.error = "Email is required"
            else {
                binding.progress.visibility = View.VISIBLE
                if ((user.email != updatedEmail) && (user.password != updatedPassword)) updateEmailPassImage()
                else if (user.email != updatedEmail) updateEmailImage()
                else if (user.password != updatedPassword) updatePasswordImage()
                else if (imageUri != null) uploadImage()
                else uploadProfile(null)
            }

        }

        binding.ppCv.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            startActivityForResult(i, 101)
        }

    }

    private fun updateEmailPassImage() {
        val user = Firebase.auth.currentUser
        user!!.updateEmail(updatedEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email updated", Toast.LENGTH_SHORT).show()
                    user.updatePassword(updatedPassword)
                        .addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show()
                                if (imageUri != null) uploadImage() else uploadProfile(null)
                            }

                        }
                } else {
                    binding.progress.visibility = View.INVISIBLE
                    Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateEmailImage() {
        val user = Firebase.auth.currentUser
        user!!.updateEmail(updatedEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email updated", Toast.LENGTH_SHORT).show()
                    if (imageUri != null) uploadImage() else uploadProfile(null)
                } else {
                    binding.progress.visibility = View.INVISIBLE
                    Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun updatePasswordImage() {
        val user = Firebase.auth.currentUser
        user!!.updatePassword(updatedPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show()
                    if (imageUri != null) uploadImage() else uploadProfile(null)
                }

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 101) {
            imageUri = data?.data
            binding.ppIv.setImageURI(imageUri)
        }
    }

    private fun uploadImage() {
        val storage = FirebaseStorage.getInstance()
        val now = Calendar.getInstance()
        val y: Int = now.get(Calendar.YEAR)
        val m: Int = now.get(Calendar.MONTH) + 1

        val d: Int = now.get(Calendar.DAY_OF_MONTH)
        val h: Int = now.get(Calendar.HOUR_OF_DAY)
        val min: Int = now.get(Calendar.MINUTE)
        val s: Int = now.get(Calendar.SECOND)
        val imageName = "image: $d-$m-$y $h:$min:$s"

        val storageRef = storage.reference.child(imageName)
        storageRef.putFile(imageUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    uploadProfile(it)
                }
            }
    }

    private fun uploadProfile(imageUri: Uri?) {

        val pp = imageUri?.toString() ?: user.pp
        user = User(id, updatedName, updatedEmail, updatedPassword, pp, phoneNo, facebookLink)
        db
            .collection("users")
            .document(id)
            .set(user)
            .addOnSuccessListener {
                binding.progress.visibility = View.INVISIBLE
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }

    }

}