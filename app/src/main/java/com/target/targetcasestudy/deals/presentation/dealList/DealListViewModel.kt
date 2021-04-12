package com.target.targetcasestudy.deals.presentation.dealList

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.target.targetcasestudy.deals.domain.DealsRepository
import com.target.targetcasestudy.deals.domain.model.DealModel
import com.target.targetcasestudy.util.DealResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DealListViewModel @Inject constructor(
        private val dealsRepository: DealsRepository
): ViewModel() {
    private var dealsDisposable: Disposable?= null

    // Update deals State in UI
    private val dealsListLiveData = MutableLiveData<DealsUIState>()
    private val dealsLoadersLiveData = MutableLiveData<DealsUIState>()
    private val dealsLiveData = MediatorLiveData<DealsUIState>()

    fun getDealsLiveData(): LiveData<DealsUIState> = dealsLiveData

    @VisibleForTesting
    fun getDealsListLiveData(): LiveData<DealsUIState> = dealsListLiveData

    init {
        dealsLiveData.addSource(dealsLoadersLiveData) { dealsLiveData.postValue(it) }
        dealsLiveData.addSource(dealsListLiveData) { dealsLiveData.value = it }
    }

    // get all deals
    fun getDeals(isRefreshing: Boolean = false) {
        dealsDisposable?.dispose()
        dealsDisposable = dealsRepository.getDeals()
                .doOnSubscribe { dealsLoadersLiveData.postValue(handleLoadingState(isRefreshing, true)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { dealsLoadersLiveData.postValue(handleLoadingState(isRefreshing, false)) }
                .subscribeWith(DealsObserver())
    }

    private fun handleLoadingState(isRefreshing: Boolean, showLoader: Boolean) =
            if (isRefreshing) DealsUIState.PullToRefresh(showLoader)
            else DealsUIState.Loading(showLoader)

    inner class DealsObserver: DisposableSingleObserver<DealResult<List<DealModel>>>() {
        override fun onSuccess(result: DealResult<List<DealModel>>?) {
            dealsListLiveData.value = when(result) {
                is DealResult.Success -> DealsUIState.Success(result.data ?: emptyList())
                is DealResult.Error -> DealsUIState.Error(result.message)
                else -> null
            }
        }

        override fun onError(e: Throwable?) {
            dealsListLiveData.value = DealsUIState.Error()
        }
    }

    override fun onCleared() {
        super.onCleared()
        dealsDisposable?.dispose()
    }
}