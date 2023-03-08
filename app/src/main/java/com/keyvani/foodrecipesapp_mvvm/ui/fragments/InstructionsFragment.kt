package com.keyvani.foodrecipesapp_mvvm.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.keyvani.foodrecipesapp_mvvm.R
import com.keyvani.foodrecipesapp_mvvm.databinding.FragmentInstructionsBinding
import com.keyvani.foodrecipesapp_mvvm.responses.Result
import com.keyvani.foodrecipesapp_mvvm.utils.Constants

class InstructionsFragment : Fragment() {
    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val myBundle: Result = args!!.getParcelable<Result>(Constants.RECIPE_RESULT_KEY) as Result
        binding.instructionsWebView.webViewClient = object : WebViewClient(){}
        val websiteUrl: String? = myBundle.sourceUrl
        if (websiteUrl != null) {
            binding.instructionsWebView.loadUrl(websiteUrl)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}