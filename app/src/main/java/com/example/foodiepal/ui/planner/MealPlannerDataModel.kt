package com.example.foodiepal.ui.planner

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

class MealPlannerDataModel(
    val breakfastDetails: String?,
    val lunchDetails: String?,
    val dinnerDetails: String?,
    val mealDateTime: LocalDate
): Serializable