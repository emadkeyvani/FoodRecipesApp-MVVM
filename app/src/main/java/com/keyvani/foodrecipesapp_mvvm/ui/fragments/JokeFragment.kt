package com.keyvani.foodrecipesapp_mvvm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.keyvani.foodrecipesapp_mvvm.databinding.FragmentJokeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JokeFragment : Fragment() {
    private var _binding: FragmentJokeBinding?=null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentJokeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}