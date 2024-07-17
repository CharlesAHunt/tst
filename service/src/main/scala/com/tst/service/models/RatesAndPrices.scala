package com.tst.service.models

object RatesAndPrices {

  final case class Rate(rateCode: String, rateGroup: String)
  final case class CabinPrice(cabinCode: String, rateCode: String, price: BigDecimal)
  final case class BestGroupPrice(cabinCode: String, rateCode: String, price: BigDecimal, rateGroup: String)

}
