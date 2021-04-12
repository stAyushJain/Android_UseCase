package com.target.targetcasestudy.deals.presentation.dealDetails

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.target.targetcasestudy.R
import com.target.targetcasestudy.deals.di.DaggerDealsComponent
import com.target.targetcasestudy.deals.domain.model.DealModel
import com.target.targetcasestudy.baseDi.BaseInjector
import com.target.targetcasestudy.baseDi.ViewModelFactory
import com.target.targetcasestudy.util.showSnackBar
import com.target.targetcasestudy.util.updateVisibility
import java.lang.IllegalStateException
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class DealDetailsFragment : Fragment() {

  private lateinit var productImageView: AppCompatImageView
  private lateinit var dealTitle: AppCompatTextView
  private lateinit var dealDescription: AppCompatTextView
  private lateinit var dealPrice: AppCompatTextView
  private lateinit var dealRegPrice: AppCompatTextView
  private lateinit var progressBar: FrameLayout

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private lateinit var dealDetailViewModel: DealDetailsViewModel
  private lateinit var dealId: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val dealIdFromArg = arguments?.getString(DEAL_ID)
    if (dealIdFromArg != null) dealId = dealIdFromArg
    else throw IllegalStateException(getString(R.string.invalid_detail_state))
    val dealsComponent = DaggerDealsComponent.builder().baseComponent(BaseInjector.getBaseComponent()).build()
    dealsComponent.inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_deal_details, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    dealDetailViewModel = ViewModelProvider(this, viewModelFactory).get(DealDetailsViewModel::class.java)
    initUI(view)
    dealDetailViewModel.getDealDetails(dealId)
    dealDetailViewModel.getDealsLiveData().observe(viewLifecycleOwner, Observer { handleResult(it) })
  }

  private fun handleResult(uiState: DealDetailsUIState?) {
    when (uiState) {
      is DealDetailsUIState.Success -> updateUI(uiState.deals)
      is DealDetailsUIState.Loading -> progressBar.updateVisibility(uiState.showLoader)
      is DealDetailsUIState.Error -> handleError(uiState)
    }
  }

  private fun initUI(view: View) {
    view.apply {
      dealTitle = findViewById(R.id.tv_title)
      dealDescription = findViewById(R.id.tv_description)
      dealPrice = findViewById(R.id.tv_price)
      dealRegPrice = findViewById(R.id.tv_reg_price)
      productImageView = findViewById(R.id.iv_product)
      progressBar = findViewById(R.id.progressBar)
    }
  }

  private fun updateUI(productDetails: DealModel) {
    dealTitle.text = productDetails.title
    dealDescription.text = productDetails.description
    dealPrice.text = productDetails.price
    dealRegPrice.text = getRegPrice()
    Glide.with(productImageView)
      .load(productDetails.imageUrl)
      .placeholder(R.drawable.placeholder)
      .error(R.drawable.placeholder)
      .into(productImageView)
  }

  private fun getRegPrice(): SpannableStringBuilder {
    val rgValue = String.format(Locale.US,"%.2f", Random.nextDouble(10.0))
    val combineValue = String.format(getString(R.string.reg_price), rgValue)
    val spannable = SpannableStringBuilder(combineValue)
    spannable.setSpan(StrikethroughSpan(), 5, combineValue.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannable
  }

  private fun handleError(result: DealDetailsUIState.Error) {
    requireActivity().showSnackBar(
      result.message ?: getString(result.messageId),
      getString(R.string.retry)
    ) { dealDetailViewModel.getDealDetails(dealId) }
  }

  companion object {
    private const val DEAL_ID = "DEAL_ID"
    fun getInstance(id: String) =
      DealDetailsFragment().apply {
        arguments = Bundle().apply {
          putString(DEAL_ID, id)
        }
      }
  }
}
