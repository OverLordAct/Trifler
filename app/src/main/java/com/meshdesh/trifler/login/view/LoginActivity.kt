package com.meshdesh.trifler.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.meshdesh.trifler.R
import com.meshdesh.trifler.signup.view.SignupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setHeader()
    }

    private fun setHeader() {
        header.setButtonClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
    }
}