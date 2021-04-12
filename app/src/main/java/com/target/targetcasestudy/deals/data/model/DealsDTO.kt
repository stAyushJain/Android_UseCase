package com.target.targetcasestudy.deals.data.model


import com.google.gson.annotations.SerializedName

data class DealsDTO(
    @SerializedName("products")
    val products: List<Product>?
)