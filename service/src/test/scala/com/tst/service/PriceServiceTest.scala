package com.tst.service

import com.tst.fixtures.TestOutputData.rateGroups
import com.tst.service.models.RatesAndPrices.CabinPrice
import com.tst.service.services.PriceService

object PriceServiceTest extends TestResource {

  test("Test price service returns a right") { _ =>
    for {
      e <- PriceService.getBestGroupPrices(InputData.rateGroups, InputData.cabinPrices).value
    } yield expect(e.isRight)
  }

  test("Test price service returns a non empty result") { _ =>
    for {
      e <- PriceService.getBestGroupPrices(InputData.rateGroups, InputData.cabinPrices).value
    } yield expect(e.toOption.getOrElse(List.empty).nonEmpty)
  }

  test("Test price service returns a failure for a bad rate group input") { _ =>
    val invalid = CabinPrice("invalid", "invalid", 0.0)
    for {
      e <- PriceService.getBestGroupPrices(InputData.rateGroups, InputData.cabinPrices.:+(invalid)).value
    } yield expect(e.isLeft)
  }

  test("Test price return empty for empty input") { _ =>
    for {
      e <- PriceService.getBestGroupPrices(Seq.empty, Seq.empty).value
    } yield expect(e.toOption.getOrElse(List.empty).isEmpty)
  }

  test("Test price return empty for empty input in param 1") { _ =>
    for {
      e <- PriceService.getBestGroupPrices(InputData.rateGroups, Seq.empty).value
    } yield expect(e.toOption.getOrElse(List.empty).isEmpty)
  }

  test("Test price return empty for empty input in param 2") { _ =>
    for {
      e <- PriceService.getBestGroupPrices(Seq.empty, InputData.cabinPrices).value
    } yield expect(e.toOption.getOrElse(List.empty).isEmpty)
  }

  test("Test price service returns all expected results") { _ =>
    for {
      e <- PriceService.getBestGroupPrices(InputData.rateGroups, InputData.cabinPrices).value
    } yield expect(e.toOption.getOrElse(List.empty).forall(rateGroups.contains))
  }

}
