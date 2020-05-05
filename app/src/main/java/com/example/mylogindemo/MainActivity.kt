package com.example.mylogindemo

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginBtn = findViewById<View>(R.id.mbtnLogin) as Button
        val registerBtn = findViewById<View>(R.id.mBtnGo) as Button



        loginBtn.setOnClickListener (View.OnClickListener{
                view -> login()
        })
        registerBtn.setOnClickListener (View.OnClickListener{
                view ->register()

        })
    }

    private fun login() {
        val emailTxt = findViewById<View>(R.id.mEdtEmail) as EditText
        val passwordTxt = findViewById<View>(R.id.mEdtPassword) as EditText

        var email = emailTxt.text.toString()
        var password = passwordTxt.text.toString()

        if (!email.isEmpty() or !password.isEmpty()) {

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener { task ->
                    startActivity(Intent(this, TimelimeActivity::class.java))
                    Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()
                })

        } else {
            Toast.makeText(this, "Error...Log in not successful", Toast.LENGTH_LONG).show()
        }
    }

    private fun register() {
        var progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait.......")
        progress.show()
        startActivity(Intent(this, RegisterActivity::class.java))
        clear()
        progress.dismiss()
    }

    private fun clear() {
        mEdtEmail.setText("")
        mEdtPassword.setText("")
    }
}
