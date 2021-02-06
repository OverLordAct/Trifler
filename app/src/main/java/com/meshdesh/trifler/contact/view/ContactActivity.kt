package com.meshdesh.trifler.contact.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.meshdesh.trifler.R
import com.meshdesh.trifler.contact.view.viewModel.ContactActivityViewModel
import com.meshdesh.trifler.contact.view.viewModel.ContactActivityViewModelImpl
import com.meshdesh.trifler.databinding.ActivityContactBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactActivity : AppCompatActivity() {

    private val viewModel: ContactActivityViewModelImpl by viewModels()
    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()
        setInitialFragment()

        viewModel.nextStepStatus.observe(this, ::observeStep)
    }

    private fun observeStep(currentStep: ContactActivityViewModel.CurrentStep) {
        when (currentStep) {
            is ContactActivityViewModel.CurrentStep.Step1 -> {
                binding.toolbar.visibility = View.GONE
            }
            is ContactActivityViewModel.CurrentStep.Step2 -> {
                binding.toolbar.title = getString(R.string.contact_step_1_title)
                binding.toolbar.subtitle = getString(R.string.contact_step_1_subtitle)
            }
        }
    }

    private fun setToolBar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setInitialFragment() {
        val fragment = AddContactFragment()
        supportFragmentManager.commit {
            add(binding.fragmentContainer.id, fragment)
            setReorderingAllowed(true)
        }
    }
}