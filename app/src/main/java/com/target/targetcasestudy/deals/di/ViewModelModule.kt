package com.target.targetcasestudy.deals.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.target.targetcasestudy.deals.presentation.dealDetails.DealDetailsViewModel
import com.target.targetcasestudy.baseDi.ViewModelFactory
import com.target.targetcasestudy.baseDi.ViewModelKey
import com.target.targetcasestudy.deals.presentation.dealList.DealListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DealListViewModel::class)
    internal abstract fun dealListViewModel(viewModel: DealListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DealDetailsViewModel::class)
    internal abstract fun dealDetailsViewModel(viewModel: DealDetailsViewModel): ViewModel
}