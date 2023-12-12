package com.example.foodiepal.ui.planner

import android.app.AlertDialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodiepal.R
import com.example.foodiepal.adapter.MealPlanListAdapter
import com.example.foodiepal.adapter.RecipeListAdapter
import com.example.foodiepal.databinding.FragmentMealPlannerBinding
import com.example.foodiepal.ui.recipe.RecipeDataModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MealPlannerFragment : Fragment() {

    private var _binding: FragmentMealPlannerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mealPlanList = mutableListOf<MealPlannerDataModel>(
        MealPlannerDataModel(
            "Sunrise Scramble",
            "Mediterranean Chickpea Salad",
            "Balsamic Glazed Chicken with Roasted Vegetables",
            LocalDate.now()
        ),
        MealPlannerDataModel(
            "Berry Bliss Pancakes",
            "Grilled Chicken Caesar Wrap",
            "Spaghetti Bolognese with Garlic Bread",
            LocalDate.now()
        ),
        MealPlannerDataModel(
            "Morning Glory Muffins",
            "Quinoa and Roasted Vegetable Bowl",
            "Grilled Shrimp Tacos with Mango Salsa",
            LocalDate.now()
        ),
        MealPlannerDataModel(
            "Golden Oat Delight",
            "Teriyaki Salmon with Brown Rice",
            "Vegetarian Stir-Fry with Tofu and Mixed Greens",
            LocalDate.now()
        ),
        MealPlannerDataModel(
            "Tropical Twist Smoothie Bowl",
            "Teriyaki Salmon with Brown Rice",
            "Beef and Broccoli Stir-Fry over Jasmine Rice",
            LocalDate.now()
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mealPlanViewModel =
            ViewModelProvider(this).get(MealPlannerModel::class.java)

        _binding = FragmentMealPlannerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var addNewMealPlanBtn = binding.addNewMealPlanBtn
        var recyclerView = binding.mealPlanRecycleView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val mealPlannerAdapter = MealPlanListAdapter(requireContext(), mealPlanList)
        recyclerView.adapter = mealPlannerAdapter

        addNewMealPlanBtn.setOnClickListener {
            var selectedDateTime: LocalDate = LocalDate.now()
            val mealPlannerFormBuilder = AlertDialog.Builder(requireContext())

            val mealPlannerFormView = layoutInflater.inflate(R.layout.meal_planner_form, null)
            mealPlannerFormBuilder.setView(mealPlannerFormView)
            val mealPlanDateField =
                mealPlannerFormView.findViewById<CalendarView>(R.id.mealPlannerCalendarView)

            mealPlanDateField.setOnDateChangeListener { view, year, month, dayOfMonth ->
                val pattern = DateTimeFormatter.ofPattern("yyyy-M-d")
                val dateStr = year.toString() + "-" + (month + 1) + "-" + dayOfMonth
                selectedDateTime = LocalDate.parse(dateStr, pattern)
            }

            mealPlannerFormBuilder.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }

            mealPlannerFormBuilder.setPositiveButton("Save Meal Plan") { dialog, which ->
                val breakfastField = mealPlannerFormView.findViewById<EditText>(R.id.breakfastField)
                val lunchField = mealPlannerFormView.findViewById<EditText>(R.id.lunchField)
                val dinnerField = mealPlannerFormView.findViewById<EditText>(R.id.dinnerField)

                mealPlanList.add(
                    MealPlannerDataModel(
                        breakfastField.text.toString(),
                        lunchField.text.toString(),
                        dinnerField.text.toString(),
                        selectedDateTime
                    )
                )
                dialog.dismiss()
                mealPlannerAdapter.notifyDataSetChanged()
                Toast.makeText(
                    requireContext(),
                    "Meal plan added successfully for ${selectedDateTime}.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val mealPlannerDialog = mealPlannerFormBuilder.create()
            mealPlannerDialog.show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}