package com.tst.service

import com.tst.fixtures.TestInputData
import com.tst.fixtures.TestOutputData.{promoCombos, promoCombosComplex, promoCombosP1, promoCombosP3}
import com.tst.service.services.PromotionService

object PromotionServiceTest extends TestResource {

  test("Test promotion service returns a non empty result") { _ =>
    for {
      e <- PromotionService.allCombinablePromotions(InputData.promotions).value
    } yield expect(e.toOption.getOrElse(List.empty).nonEmpty)
  }

  test("Test promotion service returns all expected results for all combo promos") { _ =>
    for {
      e <- PromotionService.allCombinablePromotions(InputData.promotions).value
    } yield expect(e.toOption.getOrElse(List.empty).forall(promoCombos.contains))
  }

  test("Test promotion service returns all expected results for P1") { _ =>
    for {
      e <- PromotionService.combinablePromotions(InputData.promotions, P1).value
    } yield expect(e.toOption.getOrElse(List.empty).forall(promoCombosP1.contains))
  }

  test("Test promotion service returns all expected results for P2") { _ =>
    for {
      e <- PromotionService.combinablePromotions(InputData.promotions, P3).value
    } yield expect(e.toOption.getOrElse(List.empty).forall(promoCombosP3.contains))
  }

  test("Test promotion service returns empty for no promos for any promo code") { _ =>
    for {
      e <- PromotionService.combinablePromotions(Seq.empty, P3).value
    } yield expect(e.toOption.getOrElse(List.empty) === List.empty)
  }

  test("Test promotion service should not return combos with only one result") { _ =>
    for {
      e <- PromotionService.allCombinablePromotions(TestInputData.promotionsComplex).value
    } yield {
      expect(e.toOption.getOrElse(List.empty).forall(promoCombosComplex.contains))
    }
  }
}
