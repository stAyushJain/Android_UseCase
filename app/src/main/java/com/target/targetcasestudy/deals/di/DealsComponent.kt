package com.target.targetcasestudy.deals.di

import com.target.targetcasestudy.deals.presentation.dealDetails.DealDetailsFragment
import com.target.targetcasestudy.baseDi.BaseComponent
import com.target.targetcasestudy.deals.presentation.dealList.DealListFragment
import dagger.Component

@FeatureScope
@Component(modules = [DealsModule::class, ViewModelModule::class], dependencies = [BaseComponent::class])
interface DealsComponent {
    fun inject(dealListFragment: DealListFragment)
    fun inject(dealDeatilsFragment: DealDetailsFragment)
}