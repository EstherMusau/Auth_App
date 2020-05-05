package com.example.mylogindemo

import android.app.ProgressDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
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
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {


    val mAuth= FirebaseAuth.getInstance()
     lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val regBtn=findViewById<View>(R.id.mBtnReg) as Button
        mDatabase=FirebaseDatabase.getInstance().getReference("Names")




        regBtn.setOnClickListener (View.OnClickListener{
            view-> register()
        } )
    }

    private fun register(){

        val emailtxt=findViewById<View>(R.id.mEdtEmail) as EditText
        val passwordtxt=findViewById<View>(R.id.mEdtPassWord) as EditText
        val usernametxt=findViewById<View>(R.id.mEdtusername) as EditText

        var email=emailtxt.text.toString()
        var password=passwordtxt.text.toString()
        var username=usernametxt.text.toString()
        var progress = ProgressDialog(this)
        progress.setTitle("Registering")
        progress.setMessage("Please wait.......")

        if(!email.isEmpty() && !password.isEmpty()&& !username.isEmpty()){
            progress.show()
       mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,
           OnCompleteListener { task ->

               if(task.isSuccessful){
                   var user= mAuth.currentUser
                   var uid= user!!.uid.toString()
                   mDatabase.child(uid).child("Names").setValue(username)
                   progress.dismiss()
                   Toast.makeText(this,"Sign in successful", Toast.LENGTH_LONG).show()
                   startActivity(Intent(this,TimelimeActivity::class.java))

           }
               else{
                   progress.dismiss()
               Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()}

           })
        }
        else{
            Toast.makeText(this,"Please enter the required credentials",Toast.LENGTH_LONG).show()

        }
    }
}
