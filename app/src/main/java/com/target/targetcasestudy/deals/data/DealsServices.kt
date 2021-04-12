package com.target.targetcasestudy.deals.data

import com.target.targetcasestudy.deals.data.model.DealsDTO
import com.target.targetcasestudy.deals.data.model.Product
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DealsServices {
    @GET("/mobile_case_study_deals/v1/deals")
    fun getDeals(): Single<Response<DealsDTO>>

    @GET("/mobile_case_study_deals/v1/deals/{id}")
    fun getDealDeatils(@Path("id") id: String): Single<Response<Product>>
}