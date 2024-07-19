package com.tst.service.models

final case class Promotion(code: String, notCombinableWith: Seq[String])

final case class PromotionCombo(promotionCodes: Seq[String] = Seq.empty)
