package com.tst.service.models

object Promotions {

  final case class Promotion(code: String, notCombinableWith: Seq[String])
  final case class PromotionCombo(promotionCodes: Seq[String] = Seq.empty)

}
