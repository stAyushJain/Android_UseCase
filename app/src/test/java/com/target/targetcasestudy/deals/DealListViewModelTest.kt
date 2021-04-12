package com.target.targetcasestudy.deals

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.target.targetcasestudy.deals.domain.DealsRepository
import com.target.targetcasestudy.deals.presentation.dealList.DealListViewModel
import com.target.targetcasestudy.deals.presentation.dealList.DealsUIState
import com.target.targetcasestudy.util.DealResult
import com.target.targetcasestudy.util.RxImmediateSchedulerRule
import com.target.targetcasestudy.util.getOrAwaitValue
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DealListViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Mock
    private lateinit var dealsRepository: DealsRepository

    private lateinit var dealListViewModel: DealListViewModel

    @Before
    fun setUp() {
        dealListViewModel = DealListViewModel(dealsRepository)
    }

    @Test
    fun `check deals are coming when api is successful`() {
        Mockito.`when`(dealsRepository.getDeals()).thenReturn(Single.just(DealResult.Success(MockDeals.deals)))

        dealListViewModel.getDeals()

        val value = dealListViewModel.getDealsListLiveData().getOrAwaitValue()
        assert(value is DealsUIState.Success)

        (value as DealsUIState.Success).let {
            assert(value.deals.isNotEmpty())
        }
    }

    @Test
    fun `check error is coming when api is not successful`() {
        Mockito.`when`(dealsRepository.getDeals()).thenReturn(Single.just(DealResult.Error("")))

        dealListViewModel.getDeals()

        val value = dealListViewModel.getDealsListLiveData().getOrAwaitValue()
        assert(value is DealsUIState.Error)
    }

    @Test
    fun `check loader is hiding when api throwing response`() {
        Mockito.`when`(dealsRepository.getDeals()).thenReturn(Single.just(DealResult.Error("")))

        dealListViewModel.getDeals()

        val value = dealListViewModel.getDealsLiveData().getOrAwaitValue()
        assert(value is DealsUIState.Loading)

        (value as DealsUIState.Loading).let {
            assert(!value.showLoader)
        }
    }

    @Test
    fun `check parsing is correct are coming when api is successful`() {
        Mockito.`when`(dealsRepository.getDeals()).thenReturn(Single.just(DealResult.Success(MockDeals.deals)))

        dealListViewModel.getDeals()

        val value = dealListViewModel.getDealsListLiveData().getOrAwaitValue()
        (value as DealsUIState.Success).let {
            assert(value.deals[0].id == "0")
            assert(value.deals[0].title == "Product 1")
        }
    }
}