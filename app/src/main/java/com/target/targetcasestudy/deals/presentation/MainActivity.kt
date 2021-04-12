package com.target.targetcasestudy.deals.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.target.targetcasestudy.R
import com.target.targetcasestudy.deals.presentation.dealDetails.DealDetailsFragment
import com.target.targetcasestudy.deals.domain.model.DealModel
import com.target.targetcasestudy.deals.presentation.dealList.DealInteractor
import com.target.targetcasestudy.deals.presentation.dealList.DealListFragment
import com.target.targetcasestudy.deals.presentation.payment.PaymentDialogFragment

class MainActivity : AppCompatActivity(), DealInteractor {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setDisplayShowHomeEnabled(true)
    }

    if (savedInstanceState == null)
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, DealListFragment())
        .commit()
  }

  override fun onDealClick(dealModel: DealModel) {
    supportFragmentManager.beginTransaction()
      .setCustomAnimations(
        R.anim.slide_in,
        R.anim.fade_out,
        R.anim.fade_in,
        R.anim.slide_out
      )
      .add(R.id.container, DealDetailsFragment.getInstance(dealModel.id))
      .addToBackStack(null)
      .commit()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.credit_card -> PaymentDialogFragment().show(supportFragmentManager, "CreditCardValidation")
      android.R.id.home -> onBackPressed()
      else -> return false
    }
    return true
  }
}
