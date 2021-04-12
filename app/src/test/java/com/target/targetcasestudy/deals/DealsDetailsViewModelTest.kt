package com.target.targetcasestudy.deals

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.target.targetcasestudy.deals.domain.DealsRepository
import com.target.targetcasestudy.deals.presentation.dealDetails.DealDetailsUIState
import com.target.targetcasestudy.deals.presentation.dealDetails.DealDetailsViewModel
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
class DealsDetailsViewModelTest {

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

    private lateinit var dealDetailsViewModel: DealDetailsViewModel

    @Before
    fun setUp() {
        dealDetailsViewModel = DealDetailsViewModel(dealsRepository)
    }

    @Test
    fun `check deals are coming when api is successful`() {
        Mockito.`when`(dealsRepository.getDealDetails(Mockito.anyString())).thenReturn(Single.just(DealResult.Success(MockDeals.deal)))

        dealDetailsViewModel.getDealDetails("12")

        val value = dealDetailsViewModel.getDealDetailLiveData().getOrAwaitValue()
        assert(value is DealDetailsUIState.Success)

        (value as DealDetailsUIState.Success).let {
            assert(value.deals.id == "0")
        }
    }

    @Test
    fun `check error is coming when api is not successful`() {
        Mockito.`when`(dealsRepository.getDealDetails("12")).thenReturn(Single.just(DealResult.Error("")))

        dealDetailsViewModel.getDealDetails("12")

        val value = dealDetailsViewModel.getDealDetailLiveData().getOrAwaitValue()
        assert(value is DealDetailsUIState.Error)
    }

    @Test
    fun `check loader is hiding when api throwing response`() {
        Mockito.`when`(dealsRepository.getDealDetails("12")).thenReturn(Single.just(DealResult.Error("")))

        dealDetailsViewModel.getDealDetails("12")

        val value = dealDetailsViewModel.getDealsLiveData().getOrAwaitValue()
        assert(value is DealDetailsUIState.Loading)

        (value as DealDetailsUIState.Loading).let {
            assert(!value.showLoader)
        }
    }

    @Test
    fun `check parsing is correct are coming when api is successful`() {
        Mockito.`when`(dealsRepository.getDealDetails("12")).thenReturn(Single.just(DealResult.Success(MockDeals.deal)))

        dealDetailsViewModel.getDealDetails("12")

        val value = dealDetailsViewModel.getDealDetailLiveData().getOrAwaitValue()
        (value as DealDetailsUIState.Success).let {
            assert(value.deals.id == "0")
            assert(value.deals.title == "Product 1")
        }
    }
}