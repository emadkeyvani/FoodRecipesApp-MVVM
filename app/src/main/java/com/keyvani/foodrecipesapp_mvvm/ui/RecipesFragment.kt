package com.keyvani.foodrecipesapp_mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyvani.foodrecipesapp_mvvm.adapters.RecipesAdapter
import com.keyvani.foodrecipesapp_mvvm.databinding.FragmentRecipesBinding
import com.keyvani.foodrecipesapp_mvvm.utils.NetworkResult
import com.keyvani.foodrecipesapp_mvvm.viewmodels.MainViewModel
import com.keyvani.foodrecipesapp_mvvm.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mAdapter: RecipesAdapter

    private val mainViewModel: MainViewModel by viewModels()
    private val recipesViewModel: RecipesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        readDatabase()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "readDatabaseCalled! ")
                    mAdapter.setData(database[0].foodRecipe)
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvRecipes.adapter = mAdapter
        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiDataCalled! ")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.pbLoading.visibility = View.INVISIBLE
                    binding.rvRecipes.visibility = View.VISIBLE
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    loadDataFromCash()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()

                }
                is NetworkResult.Loading -> {
                    binding.rvRecipes.visibility = View.INVISIBLE
                    binding.pbLoading.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun loadDataFromCash() {
       lifecycleScope.launch {
           mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
               if (database.isNotEmpty()) {
                   Log.d("RecipesFragment", "readDatabaseCalled! ")
                   mAdapter.setData(database[0].foodRecipe)
               }
           }
       }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}