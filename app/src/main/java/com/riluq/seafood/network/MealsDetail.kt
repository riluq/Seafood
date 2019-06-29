package com.riluq.seafood.network

import com.squareup.moshi.Json

data class MealsDetail (

    @Json(name = "idMeal")
    val id: String,
    @Json(name = "strMealThumb")
    val mealImage: String,
    @Json(name = "strMeal")
    val mealName: String,
    @Json(name = "strCategory")
    val mealCategory: String,
    @Json(name = "strArea")
    val mealArea: String,
    @Json(name = "strInstructions")
    val mealInstructions: String,
    @Json(name = "strYoutube")
    val mealYoutube: String,
    @Json(name = "strSource")
    val mealSource: String

)