package com.example.firebaseregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    lateinit var name: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var registerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connect UI elements
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        registerBtn = findViewById(R.id.registerBtn)

        registerBtn.setOnClickListener {

            val nameText = name.text.toString()
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            // Basic validation
            if (nameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(nameText, emailText, passwordText)

            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("Users")

            reference.push().setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show()

                    name.text?.clear()
                    email.text?.clear()
                    password.text?.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
