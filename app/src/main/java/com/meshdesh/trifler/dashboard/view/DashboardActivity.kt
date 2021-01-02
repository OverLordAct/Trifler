package com.meshdesh.trifler.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.meshdesh.trifler.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_onboarded_category_card.view.*

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setInitialFragment()
    }

    private fun setInitialFragment() {
        val fragment = OnboardedDashboardFragment.newInstance()

        supportFragmentManager.commit {
            add(R.id.fragmentContainer, fragment)
            setReorderingAllowed(true)
        }
    }
}

class CategoriesAdapter : RecyclerView.Adapter<CategoriesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.setData("Main text: $position", "Secondary Text: $position")
    }

    override fun getItemCount(): Int {
        return 10
    }
}

class CategoriesViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.custom_onboarded_category_card, parent, false
    )
) {
    fun setData(text1: String, text2: String) {
        itemView.mainText.text = text1
        itemView.secondaryText.text = text2
    }
}