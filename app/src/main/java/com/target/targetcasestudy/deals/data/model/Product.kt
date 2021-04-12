package com.target.targetcasestudy.deals.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("aisle")
    val aisle: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("regular_price")
    val regularPrice: RegularPrice?,
    @SerializedName("title")
    val title: String?
)