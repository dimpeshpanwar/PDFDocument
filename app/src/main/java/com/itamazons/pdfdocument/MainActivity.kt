package com.itamazons.googlelogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient


class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    var signInButton: SignInButton? = null
    private var googleApiClient: GoogleApiClient? = null
    var textView: TextView? = null
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()



        signInButton = findViewById<View>(R.id.sign_in_button) as SignInButton
        signInButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
                startActivityForResult(intent, RC_SIGN_IN)
            }
        })

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult?) {
        if (result!!.isSuccess) {
            gotoProfile()
        } else {
            Toast.makeText(applicationContext, "Sign in cancel", Toast.LENGTH_LONG).show()
        }
    }

    private fun gotoProfile() {
        val intent = Intent(this@MainActivity, ProfileActivity::class.java)
        startActivity(intent)
    }

}