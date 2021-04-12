package com.target.targetcasestudy.deals.data.model

import com.google.gson.annotations.SerializedName

data class RegularPrice(
    @SerializedName("amount_in_cents")
    val amountInCents: Int?,
    @SerializedName("currency_symbol")
    val currencySymbol: String?,
    @SerializedName("display_string")
    val displayString: String?
)