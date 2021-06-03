package com.example.yourskinhealthcare.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.yourskinhealthcare.R
import com.example.yourskinhealthcare.ui.home.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.HashMap

class SignUpActivity : AppCompatActivity() {

    companion object {
        const val TAG = "TAG"
    }

    private lateinit var mFullName: EditText
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var mConfirmPassword: EditText
    private lateinit var mRegisterBtn: Button
    private lateinit var mLoginBtn: TextView
    private lateinit var fStore: FirebaseFirestore
    private lateinit var userID: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFullName = findViewById(R.id.reg_fullName)
        mEmail = findViewById(R.id.reg_email)
        mPassword = findViewById(R.id.reg_password)
        mConfirmPassword = findViewById(R.id.confirmPassword)
        mRegisterBtn = findViewById(R.id.signUp)
        mLoginBtn = findViewById(R.id.Cancel)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        if (auth.currentUser != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }


        mRegisterBtn.setOnClickListener(View.OnClickListener {
            val email = mEmail.text.toString().trim { it <= ' ' }
            val password = mPassword.text.toString().trim { it <= ' ' }
            val confirmPassword = mConfirmPassword.text.toString().trim { it <= ' ' }
            val fullName = mFullName.text.toString()
            if (TextUtils.isEmpty(email)) {
                mEmail.error = "Email is Required."
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.error = "Password is Required."
                return@OnClickListener
            }
            if (password.length < 6) {
                mPassword.error = "Password Must be >= 6 Characters"
                return@OnClickListener
            }
            if (confirmPassword != password) {
                mConfirmPassword.error = "Password is not matching!"
                return@OnClickListener
            }

            progressBar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this@SignUpActivity, "User Created.", Toast.LENGTH_SHORT).show()
                    userID = auth.currentUser!!.uid
                    val documentReference = fStore.collection("users").document(
                        userID
                    )
                    val user: MutableMap<String, Any> =
                        HashMap()
                    user["fName"] = fullName
                    user["email"] = email
                    documentReference.set(user).addOnSuccessListener {
                        Log.d(
                            TAG,
                            "onSuccess: user Profile is created for $userID"
                        )
                    }.addOnFailureListener { e ->
                        Log.d(
                            TAG,
                            "onFailure: $e"
                        )
                    }
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Error ! " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.visibility = View.GONE
                }
            }
        })
        mLoginBtn.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    LoginActivity::class.java
                )
            )
        })
    }
}