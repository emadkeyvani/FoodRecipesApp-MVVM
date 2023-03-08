package com.keyvani.foodrecipesapp_mvvm.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import com.keyvani.foodrecipesapp_mvvm.responses.Result
import com.keyvani.foodrecipesapp_mvvm.R
import com.keyvani.foodrecipesapp_mvvm.bindingadapters.RecipesRowBinding
import com.keyvani.foodrecipesapp_mvvm.databinding.FragmentOverViewBinding
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.RECIPE_RESULT_KEY

class OverViewFragment : Fragment() {
    private var _binding: FragmentOverViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val myBundle: Result = args!!.getParcelable<Result>(RECIPE_RESULT_KEY) as Result
        binding.apply {
            mainImageView.load(myBundle.image)
            titleTextView.text = myBundle.title
            likesTextView.text = myBundle.aggregateLikes.toString()
            timeTextView.text = myBundle.readyInMinutes.toString()
            RecipesRowBinding.parseHtml(summaryTextView, myBundle.summary)

            updateColors(myBundle.vegetarian, vegetarianTextView, vegetarianImageView)
            updateColors(myBundle.vegan, veganTextView, veganImageView)
            updateColors(myBundle.cheap, cheapTextView, cheapImageView)
            updateColors(myBundle.dairyFree, dairyFreeTextView, dairyFreeImageView)
            updateColors(myBundle.glutenFree, glutenFreeTextView, glutenFreeImageView)
            updateColors(myBundle.veryHealthy, healthyTextView, healthyImageView)
        }

    }

    private fun updateColors(stateIsOn: Boolean, textView: TextView, imageView: ImageView) {
        if (stateIsOn) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}