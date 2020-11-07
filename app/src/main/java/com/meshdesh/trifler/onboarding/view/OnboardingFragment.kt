package com.meshdesh.trifler.onboarding.view

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.meshdesh.trifler.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_onboarding.*

class OnboardingFragment : Fragment() {

    companion object {
        private const val ARGS = "Args"

        @Parcelize
        data class Args(
            @DrawableRes val mainImage: Int,
            val title: String,
            val subtitle: String
        ) : Parcelable

        fun newInstance(args: Args): OnboardingFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARGS, args)
            val fragment = OnboardingFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        (arguments?.getParcelable(ARGS) as? Args)?.let {
            centralImage.setImageResource(it.mainImage)
            title.text = it.title
            subtitle.text = it.subtitle
        }
    }

}