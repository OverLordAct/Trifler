package com.meshdesh.trifler.contact.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.meshdesh.trifler.R
import com.meshdesh.trifler.contact.viewModel.AddContactActivityViewModel
import com.meshdesh.trifler.contact.viewModel.AddContactActivityViewModelImpl
import com.meshdesh.trifler.databinding.ActivityContactBinding
import com.meshdesh.trifler.util.setInvisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactActivity : AppCompatActivity(), SendRequestBottomSheet.OnClickListener {

    private val viewModel: AddContactActivityViewModelImpl by viewModels()
    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()
        // setInitialFragment()

        viewModel.currentStepStatus.observe(this, ::observeStep)
    }

    private fun observeStep(uiState: AddContactActivityViewModel.UiState) {
        when (uiState) {
            is AddContactActivityViewModel.UiState.Step1 -> {
                binding.toolbar.setInvisible()
                setInitialFragment()
            }
            is AddContactActivityViewModel.UiState.Step2 -> {
                binding.toolbar.setInvisible()

                val args = SendRequestBottomSheet.Companion.Args(uiState.contactNumber)
                val bottomSheet = SendRequestBottomSheet.create(args)
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }
            is AddContactActivityViewModel.UiState.Step3 -> {
                binding.toolbar.setInvisible()
                onSendRequest(uiState.contactNumber)
            }
            is AddContactActivityViewModel.UiState.Step4 -> {
                supportFragmentManager.commit {
                    val fragment = AddContactSuccessFragment.create()
                    replace(binding.fragmentContainer.id, fragment)
                    setReorderingAllowed(true)
                }
            }
            is AddContactActivityViewModel.UiState.Finish -> {
                finish()
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
        val fragment = AddContactNumberFragment()
        supportFragmentManager.commit {
            add(binding.fragmentContainer.id, fragment)
            // addToBackStack(null)
            setReorderingAllowed(true)
        }
    }

    override fun onContinue() {
        // TODO: 15-08-2021 Handle this
    }

    override fun onSendRequest(contactNumber: String) {
        val args = AddContactDetailsFragment.Companion.Args(contactNumber)
        val fragment = AddContactDetailsFragment.create(args)
        supportFragmentManager.commit {
            replace(binding.fragmentContainer.id, fragment)
        }
    }
}