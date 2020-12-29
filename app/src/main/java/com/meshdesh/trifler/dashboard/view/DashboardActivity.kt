package com.meshdesh.trifler.dashboard.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meshdesh.trifler.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }
}