package com.example.mylogindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.mEdtEmail
import kotlinx.android.synthetic.main.activity_register.*
import java.util.jar.Attributes

class RegisterActivity : AppCompatActivity() {


    val mAuth= FirebaseAuth.getInstance()
     lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val regBtn=findViewById<View>(R.id.mBtnGo) as Button
        mDatabase=FirebaseDatabase.getInstance().getReference("Names")




        mBtnGo.setOnClickListener { View.OnClickListener{
            View-> register()
        } }
    }

    private fun register(){

        val emailtxt=findViewById<View>(R.id.mEdtEmail) as EditText
        val passwordtxt=findViewById<View>(R.id.mEdtPassWord) as EditText
        val usernametxt=findViewById<View>(R.id.mEdtusername) as EditText

        var email=mEdtEmail.text.toString()
        var password=mEdtPassWord.text.toString()
        var username=mEdtusername.text.toString()

        if(!email.isEmpty() && password.isEmpty()&& username.isEmpty()){
       mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,
           OnCompleteListener { task ->

               if(task.isSuccessful){
                   var user= mAuth.currentUser
                   var uid= user!!.uid.toString()
                   mDatabase.child(uid).child("Names").setValue(username)
                   Toast.makeText(this,"Sign in successful", Toast.LENGTH_LONG).show()
           }
               else{
               Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()}
           })
        }
        else{
            Toast.makeText(this,"Please enter the required credentials",Toast.LENGTH_LONG).show()

        }
    }
}
