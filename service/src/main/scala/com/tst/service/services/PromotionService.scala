package com.tst.service.services

import cats.data.EitherT
import cats.data.EitherT.rightT
import cats.effect.IO
import com.tst.service.models.{Promotion, PromotionCombo, TSTError}

object PromotionService {

  def allCombinablePromotions(allPromotions: Seq[Promotion]): EitherT[IO, TSTError, Seq[PromotionCombo]] =
    rightT[IO, TSTError] {
      allPromotions.map(promo => PromotionCombo(allPromotions.diff(promo.notCombinableWith).map(_.code)))
    }

  def combinablePromotions(
    promotionCode: String,
    allPromotions: Seq[Promotion]
  ): EitherT[IO, TSTError, Seq[PromotionCombo]] = rightT[IO, TSTError] {
    PromotionCombo(
      allPromotions
        .filterNot(promo => promo.notCombinableWith.contains(promotionCode) || promo.code == promotionCode)
        .map(_.code)
    )
  }
}
