package com.tst.fixtures

import com.tst.service.{CA, CB, M1, Military, P1, P2, P3, P4, P5, S1, Senior}
import com.tst.service.models.PromotionCombo
import com.tst.service.models.RatesAndPrices.BestGroupPrice

object TestOutputData {
  val rateGroups: Seq[BestGroupPrice] =
    Seq(
      BestGroupPrice(CA, M1, 200.00, Military),
      BestGroupPrice(CA, S1, 225.00, Senior),
      BestGroupPrice(CB, M1, 230.00, Military),
      BestGroupPrice(CB, S1, 245.00, Senior)
    )

  val promoCombos: Seq[PromotionCombo] = Seq(
    PromotionCombo(Seq(P1, P2)),
    PromotionCombo(Seq(P1, P4, P5)),
    PromotionCombo(Seq(P2, P3)),
    PromotionCombo(Seq(P3, P4, P5))
  )

  val promoCombosP1: Seq[PromotionCombo] = Seq(
    PromotionCombo(Seq(P1, P2)),
    PromotionCombo(Seq(P1, P4, P5))
  )

  val promoCombosP3: Seq[PromotionCombo] = Seq(PromotionCombo(Seq(P2, P3)), PromotionCombo(Seq(P3, P4, P5)))

}
