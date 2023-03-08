package com.keyvani.foodrecipesapp_mvvm.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyvani.foodrecipesapp_mvvm.R
import com.keyvani.foodrecipesapp_mvvm.adapters.IngredientsAdapter
import com.keyvani.foodrecipesapp_mvvm.adapters.RecipesAdapter
import com.keyvani.foodrecipesapp_mvvm.databinding.FragmentIngredientsBinding
import com.keyvani.foodrecipesapp_mvvm.databinding.FragmentRecipesBinding
import com.keyvani.foodrecipesapp_mvvm.responses.Result
import com.keyvani.foodrecipesapp_mvvm.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mAdapter: IngredientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: Result = args!!.getParcelable<Result>(Constants.RECIPE_RESULT_KEY) as Result
        setupRecyclerView()
        myBundle.extendedIngredients.let { mAdapter.setData(it) }









    }




    private fun setupRecyclerView(){
        binding.ingredientsRecyclerview.adapter = mAdapter
        binding.ingredientsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}