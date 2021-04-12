package com.target.targetcasestudy.deals.di

import com.target.targetcasestudy.deals.data.DealsRepositoryImp
import com.target.targetcasestudy.deals.data.DealsServices
import com.target.targetcasestudy.deals.domain.DealsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class DealsModule {

    @Provides
    fun provideDealsService(retrofit: Retrofit) = retrofit.create(DealsServices::class.java)

    @Provides
    fun provideDealsRepository(dealsRepositoryImp: DealsRepositoryImp): DealsRepository = dealsRepositoryImp
}