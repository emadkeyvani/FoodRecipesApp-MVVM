package com.keyvani.foodrecipesapp_mvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.keyvani.foodrecipesapp_mvvm.databinding.RecipesBottomSheetBinding
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.DEFAULT_DIET_TYPE
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.DEFAULT_MEAL_TYPE
import com.keyvani.foodrecipesapp_mvvm.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RecipesBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: RecipesBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val recipesViewModel: RecipesViewModel by viewModels()
    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipesBottomSheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner){
            mealTypeChip = it.selectedMealType
            dietTypeChip = it.selectedDietType
            updateChip(it.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChip(it.selectedDietTypeId, binding.dietTypeChipGroup)
        }

        binding.apply {
            mealTypeChipGroup.setOnCheckedStateChangeListener{group , selectedChipId ->
                val chip= group.findViewById<Chip>(selectedChipId.first())
                val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
                mealTypeChip = selectedMealType
                mealTypeChipId=selectedChipId.first()
            }
            dietTypeChipGroup.setOnCheckedStateChangeListener { group, selectedChipId ->
                val chip = group.findViewById<Chip>(selectedChipId.first())
                val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
                dietTypeChip = selectedDietType
                dietTypeChipId = selectedChipId.first()
            }
            applyBtn.setOnClickListener {
                recipesViewModel.saveMealAndDietType(
                    mealTypeChip,
                    mealTypeChipId,
                    dietTypeChip,
                    dietTypeChipId
                )
                val action =
                    RecipesBottomSheetFragmentDirections.actionRecipesBottomSheetFragmentToRecipesFragment(true)
                findNavController().navigate(action)
            }

        }

    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                val targetView = chipGroup.findViewById<Chip>(chipId)
                targetView.isChecked = true
                chipGroup.requestChildFocus(targetView, targetView)
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}