package com.target.targetcasestudy.deals.data

import com.target.targetcasestudy.deals.data.model.DealsDTO
import com.target.targetcasestudy.deals.data.model.Product
import com.target.targetcasestudy.deals.domain.model.DealModel
import javax.inject.Inject

class DealsServiceMapper @Inject constructor() {
    fun mapToDomainList(deal: DealsDTO?): List<DealModel> {
        val deals = mutableListOf<DealModel>()
        deal?.products?.let { safeDeals ->
            safeDeals.forEach { deals.add(
                DealModel(
                    id = it.id.orEmpty(),
                    title = it.title.orEmpty(),
                    description = it.description.orEmpty(),
                    price = it.regularPrice?.displayString.orEmpty(),
                    aisle = it.aisle.orEmpty(),
                    imageUrl = it.imageUrl.orEmpty()
                )
            ) }
        }
        return deals
    }

    fun mapToDomain(deal: Product): DealModel {
        return DealModel(
            id = deal.id.orEmpty(),
            title = deal.title.orEmpty(),
            description = deal.description.orEmpty(),
            price = deal.regularPrice?.displayString.orEmpty(),
            aisle = deal.aisle.orEmpty(),
            imageUrl = deal.imageUrl.orEmpty()
        )
    }
}