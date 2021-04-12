package com.target.targetcasestudy.deals.presentation.dealList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.target.targetcasestudy.R
import com.target.targetcasestudy.baseDi.BaseInjector
import com.target.targetcasestudy.baseDi.ViewModelFactory
import com.target.targetcasestudy.deals.domain.model.DealModel
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.target.targetcasestudy.deals.di.DaggerDealsComponent
import com.target.targetcasestudy.util.hide
import com.target.targetcasestudy.util.showSnackBar
import com.target.targetcasestudy.util.updateVisibility

class DealListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private lateinit var dealListViewModel: DealListViewModel
  private lateinit var dealItemAdapter: DealItemAdapter
  private lateinit var progressBar: ProgressBar
  private lateinit var swipeRefreshView: SwipeRefreshLayout
  private var dealInteractor: DealInteractor? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val dealsComponent = DaggerDealsComponent.builder().baseComponent(BaseInjector.getBaseComponent()).build()
    dealsComponent.inject(this)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    dealInteractor = context as? DealInteractor
  }

  override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    dealListViewModel = ViewModelProvider(this, viewModelFactory).get(DealListViewModel::class.java)
    val view =  inflater.inflate(R.layout.fragment_deal_list, container, false)
    progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
    swipeRefreshView = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_view).apply { setOnRefreshListener(this@DealListFragment) }
    initDealList(view)
    return view
  }

  override fun onRefresh() {
    dealListViewModel.getDeals(true)
  }

  private fun initDealList(view: View) {
    view.findViewById<RecyclerView>(R.id.recycler_view).apply {
      layoutManager = LinearLayoutManager(requireContext())
      dealItemAdapter = DealItemAdapter { onDealClick(it) }
      adapter = dealItemAdapter
      val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
      addItemDecoration(dividerItemDecoration)
      }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    dealListViewModel.getDealsLiveData().observe(viewLifecycleOwner, Observer { result ->
      updateView(result)
    })
    dealListViewModel.getDeals()
  }

  private fun updateView(result: DealsUIState?) {
    when (result) {
      is DealsUIState.Success -> dealItemAdapter.submitList(result.deals)
      is DealsUIState.Loading -> progressBar.updateVisibility(result.showLoader)
      is DealsUIState.Error -> handleError(result)
      is DealsUIState.PullToRefresh -> {
        progressBar.hide()
        swipeRefreshView.isRefreshing = result.showLoader
      }
    }
  }

  private fun handleError(result: DealsUIState.Error) {
    requireActivity().showSnackBar(
            result.message ?: getString(result.messageId),
            getString(R.string.retry)
    ) { dealListViewModel.getDeals() }
  }

  private fun onDealClick(dealModel: DealModel) {
    dealInteractor?.onDealClick(dealModel)
  }

}
