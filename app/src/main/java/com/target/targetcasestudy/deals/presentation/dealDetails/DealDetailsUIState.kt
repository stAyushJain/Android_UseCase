package com.target.targetcasestudy.deals.presentation.dealDetails

import androidx.annotation.StringRes
import com.target.targetcasestudy.R
import com.target.targetcasestudy.deals.domain.model.DealModel

sealed class DealDetailsUIState {
    data class Loading(val showLoader: Boolean) : DealDetailsUIState()
    data class Success(val deals: DealModel): DealDetailsUIState()
    data class Error(val message: String? = null, @StringRes val messageId: Int = R.string.something_went_wrong): DealDetailsUIState()
}