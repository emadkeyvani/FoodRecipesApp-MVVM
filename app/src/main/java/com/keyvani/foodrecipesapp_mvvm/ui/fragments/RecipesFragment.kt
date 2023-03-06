package com.keyvani.foodrecipesapp_mvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyvani.foodrecipesapp_mvvm.R
import com.keyvani.foodrecipesapp_mvvm.adapters.RecipesAdapter
import com.keyvani.foodrecipesapp_mvvm.databinding.FragmentRecipesBinding
import com.keyvani.foodrecipesapp_mvvm.utils.NetworkListener
import com.keyvani.foodrecipesapp_mvvm.utils.NetworkResult
import com.keyvani.foodrecipesapp_mvvm.utils.observeOnce
import com.keyvani.foodrecipesapp_mvvm.viewmodels.MainViewModel
import com.keyvani.foodrecipesapp_mvvm.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class RecipesFragment : Fragment() , SearchView.OnQueryTextListener{

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    private val recipesViewModel: RecipesViewModel by viewModels()
    private val args by navArgs<RecipesFragmentArgs>()

    @Inject
    lateinit var networkListener: NetworkListener

    @Inject
    lateinit var mAdapter: RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        recipesViewModel.readBackOnline.observe(viewLifecycleOwner) {
            recipesViewModel.backOnline = it
        }
        //Check InternetConnection
        lifecycleScope.launchWhenStarted {
            networkListener.checkNetworkAvailability(requireContext())
                .collect {
                    Log.d("NetworkListener", it.toString())
                    recipesViewModel.networkStatus = it
                    recipesViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.recipes_menu, menu)
                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.setOnQueryTextListener(this@RecipesFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        binding.apply {
            recipesFab.setOnClickListener {
                if (recipesViewModel.networkStatus) {
                    findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheetFragment)
                } else {
                    recipesViewModel.showNetworkStatus()
                }
            }
        }
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("RecipesFragment", "readDatabaseCalled! ")
                    mAdapter.setData(database[0].foodRecipe)
                } else {
                    requestApiData()
                }
            }
        }
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
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()

                }
                is NetworkResult.Loading -> {
                    binding.rvRecipes.visibility = View.INVISIBLE
                    binding.pbLoading.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "readDatabaseCalled! ")
                    mAdapter.setData(database[0].foodRecipe)
                }
            }
        }

    }

    private fun setupRecyclerView() {
        binding.rvRecipes.adapter = mAdapter
        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
      if (query != null) {
          searchApiData(query)
      }
      return true
    }

     override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    private fun searchApiData(searchQuery: String) {

        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchRecipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val foodRecipe = response.data
                    binding.pbLoading.visibility = View.INVISIBLE
                    binding.rvRecipes.visibility = View.VISIBLE
                    foodRecipe?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    binding.rvRecipes.visibility = View.INVISIBLE
                    binding.pbLoading.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}