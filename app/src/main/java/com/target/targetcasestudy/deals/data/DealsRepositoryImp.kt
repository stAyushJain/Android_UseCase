package com.target.targetcasestudy.deals.data

import com.target.targetcasestudy.deals.domain.DealsRepository
import com.target.targetcasestudy.deals.domain.model.DealModel
import com.target.targetcasestudy.util.DealResult
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DealsRepositoryImp @Inject constructor(
    private val dealsServices: DealsServices,
    private val dealsServiceMapper: DealsServiceMapper
): DealsRepository {

    override fun getDeals(): Single<DealResult<List<DealModel>>> {
        return dealsServices.getDeals().map {
            if (it.isSuccessful) {
                DealResult.Success<List<DealModel>>(dealsServiceMapper.mapToDomainList(it.body()))
            } else DealResult.Error<List<DealModel>>(it.errorBody().toString())
        }.onErrorReturn {
            // TODO("Handle All Error Case based on @Throwable")
            DealResult.Error<List<DealModel>>("Oops Something Went Wrong")
        }
    }

    override fun getDealDetails(id: String): Single<DealResult<DealModel>> {
        return dealsServices.getDealDeatils(id).map {
            if (it.isSuccessful && it.body() != null) {
                DealResult.Success<DealModel>(dealsServiceMapper.mapToDomain(it.body()!!))
            } else DealResult.Error<DealModel>(it.errorBody().toString())
        }.onErrorReturn {
            // TODO("Handle All Error Case based on @Throwable")
            DealResult.Error<DealModel>("Oops Something Went Wrong")
        }
    }
}