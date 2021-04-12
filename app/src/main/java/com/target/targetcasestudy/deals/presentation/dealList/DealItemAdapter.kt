package com.target.targetcasestudy.deals.presentation.dealList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.target.targetcasestudy.R
import com.target.targetcasestudy.deals.domain.model.DealModel

class DealItemAdapter(val clickListener: (DealModel) -> Unit) : ListAdapter<DealModel, DealItemAdapter.DealItemViewHolder>(DealsDiffCallBack()) {

  class DealsDiffCallBack: DiffUtil.ItemCallback<DealModel>() {
    override fun areItemsTheSame(oldItem: DealModel, newItem: DealModel): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DealModel, newItem: DealModel): Boolean {
      return oldItem == newItem
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealItemViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.item_deal_list, parent, false)
    return DealItemViewHolder(view)
  }

  override fun onBindViewHolder(viewHolder: DealItemViewHolder, position: Int) {
    viewHolder.bind(getItem(position))
  }

  inner class DealItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val productTitle = itemView.findViewById<AppCompatTextView>(R.id.tv_title)
    private val productImage = itemView.findViewById<AppCompatImageView>(R.id.iv_product)
    private val productPrice = itemView.findViewById<AppCompatTextView>(R.id.tv_price)
    private val productAisle = itemView.findViewById<AppCompatTextView>(R.id.tv_aisle)
    fun bind(item: DealModel) {
      productTitle.text = item.title
      productPrice.text = item.price
      productAisle.text = item.aisle
      Glide.with(productImage)
        .load(item.imageUrl)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(productImage)
      itemView.setOnClickListener { clickListener(item) }
    }
  }
}