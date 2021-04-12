package com.target.targetcasestudy.deals.presentation.dealList

import com.target.targetcasestudy.deals.domain.model.DealModel

interface DealInteractor {
    fun onDealClick(dealModel: DealModel)
}