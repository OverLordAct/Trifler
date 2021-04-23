package com.meshdesh.trifler.category.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import com.meshdesh.trifler.databinding.CustomCategoryCardBinding

class CustomCategoryCard(
    context: Context,
    attributeSet: AttributeSet
) : MaterialCardView(context, attributeSet) {

    private var binding: CustomCategoryCardBinding = CustomCategoryCardBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setCategory(category: String) {
        binding.categoryName.text = category
    }
}