package com.target.targetcasestudy.deals.domain

import com.target.targetcasestudy.deals.domain.model.DealModel
import com.target.targetcasestudy.util.DealResult
import io.reactivex.rxjava3.core.Single

interface DealsRepository {
    fun getDeals() : Single<DealResult<List<DealModel>>>
    fun getDealDetails(id: String) : Single<DealResult<DealModel>>
}