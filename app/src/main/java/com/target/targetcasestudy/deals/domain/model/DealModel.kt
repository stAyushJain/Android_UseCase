package com.target.targetcasestudy.deals.domain.model

data class DealModel(
  val id: String,
  val title: String,
  val description: String,
  val price: String,
  val aisle: String,
  val imageUrl: String
)