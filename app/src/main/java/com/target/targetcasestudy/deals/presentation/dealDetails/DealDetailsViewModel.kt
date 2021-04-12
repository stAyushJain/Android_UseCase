package com.target.targetcasestudy.deals.presentation.dealDetails

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.target.targetcasestudy.deals.domain.DealsRepository
import com.target.targetcasestudy.deals.domain.model.DealModel
import com.target.targetcasestudy.deals.presentation.dealList.DealsUIState
import com.target.targetcasestudy.util.DealResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DealDetailsViewModel @Inject constructor(
    private val dealsRepository: DealsRepository
): ViewModel() {
    private var dealsDisposable: Disposable?= null
    private var cachedDetails: DealModel? = null

    // Update deals State in UI
    private val dealDetailsLiveData = MutableLiveData<DealDetailsUIState>()
    private val dealsLoadersLiveData = MutableLiveData<DealDetailsUIState>()

    private val dealsLiveData = MediatorLiveData<DealDetailsUIState>()
    fun getDealsLiveData(): LiveData<DealDetailsUIState> = dealsLiveData

    @VisibleForTesting
    fun getDealDetailLiveData(): LiveData<DealDetailsUIState> = dealDetailsLiveData

    init {
        dealsLiveData.addSource(dealsLoadersLiveData) { dealsLiveData.postValue(it) }
        dealsLiveData.addSource(dealDetailsLiveData) { dealsLiveData.value = it }
    }

    // get deal detail
    fun getDealDetails(dealId: String) {
        if(cachedDetails == null) {
            dealsDisposable?.dispose()
            dealsDisposable = dealsRepository.getDealDetails(dealId)
                .doOnSubscribe { dealsLoadersLiveData.postValue(DealDetailsUIState.Loading(true)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { dealsLoadersLiveData.postValue(DealDetailsUIState.Loading(false)) }
                .subscribeWith(DealsObserver())
        } else {
            dealsLiveData.value = DealDetailsUIState.Success(cachedDetails!!)
        }
    }

    inner class DealsObserver: DisposableSingleObserver<DealResult<DealModel>>() {
        override fun onSuccess(result: DealResult<DealModel>?) {
            dealDetailsLiveData.value = when(result) {
                is DealResult.Success -> if (result.data != null) {
                    cachedDetails = result.data
                    DealDetailsUIState.Success(result.data)
                } else DealDetailsUIState.Error()
                is DealResult.Error -> DealDetailsUIState.Error(result.message)
                else -> null
            }
        }

        override fun onError(e: Throwable?) {
            dealDetailsLiveData.value = DealDetailsUIState.Error()
        }
    }

    override fun onCleared() {
        super.onCleared()
        dealsDisposable?.dispose()
    }
}