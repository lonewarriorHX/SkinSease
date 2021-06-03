package com.example.yourskinhealthcare.authentication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.yourskinhealthcare.ui.home.HomeActivity
import com.example.yourskinhealthcare.R
import com.example.yourskinhealthcare.ui.home.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var mEmail: EditText
    lateinit var mPassword: EditText
    lateinit var mLoginBtn: Button
    lateinit var mSignUpBtn: TextView
    lateinit var forgotTextLink: TextView
    lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mEmail = findViewById(R.id.login_email)
        mPassword = findViewById(R.id.editPassword)
        fAuth = FirebaseAuth.getInstance()
        mLoginBtn = findViewById(R.id.signIn)
        mSignUpBtn = findViewById(R.id.signUp)
        forgotTextLink = findViewById(R.id.forgotPassword)

        if (fAuth!!.currentUser != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        mLoginBtn.setOnClickListener(View.OnClickListener {
            val email = mEmail.getText().toString().trim { it <= ' ' }
            val password = mPassword.getText().toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required.")
                return@OnClickListener
            }
            if (password.length < 6) {
                mPassword.setError("Password Must be >= 6 Characters")
                return@OnClickListener
            }

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Logged in Successfully", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error ! " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
        mSignUpBtn.setOnClickListener(View.OnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        })
        forgotTextLink.setOnClickListener(View.OnClickListener { v ->
            val resetMail = EditText(v.context)
            val passwordResetDialog = AlertDialog.Builder(v.context)
            passwordResetDialog.setTitle("Reset Password ?")
            passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.")
            passwordResetDialog.setView(resetMail)
            passwordResetDialog.setPositiveButton(
                "Yes"
            ) { dialog, which -> // extract the email and send reset link
                val mail = resetMail.text.toString()
                fAuth!!.sendPasswordResetEmail(mail).addOnSuccessListener {
                    Toast.makeText(
                        this@LoginActivity,
                        "Reset Link Sent To Your Email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this@LoginActivity,
                        "Error ! Reset Link is Not Sent" + e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            passwordResetDialog.setNegativeButton(
                "No"
            ) { dialog, which ->
            }
            passwordResetDialog.create().show()
        })
    }
}