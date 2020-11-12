package com.meshdesh.trifler.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.meshdesh.trifler.R
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
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}