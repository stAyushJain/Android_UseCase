package com.target.targetcasestudy.deals.presentation.dealList

import androidx.annotation.StringRes
import com.target.targetcasestudy.R
import com.target.targetcasestudy.deals.domain.model.DealModel

sealed class DealsUIState {
    data class Loading(val showLoader: Boolean) : DealsUIState()
    data class PullToRefresh(val showLoader: Boolean) : DealsUIState()
    data class Success(val deals: List<DealModel>): DealsUIState()
    data class Error(val message: String? = null, @StringRes val messageId: Int = R.string.something_went_wrong): DealsUIState()
}
