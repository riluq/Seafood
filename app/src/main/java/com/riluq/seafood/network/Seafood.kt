package com.riluq.seafood.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Seafood(
    @Json(name = "idMeal")
    val id: String? = null,
    @Json(name = "strMealThumb")
    val seafoodImage: String? = null,
    @Json(name = "strMeal")
    val seafoodName: String? = null
): Parcelable