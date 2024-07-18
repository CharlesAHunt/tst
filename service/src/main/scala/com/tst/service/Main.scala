package com.tst.service

import cats.effect.{ExitCode, IO, IOApp}
import com.tst.service.models.Promotion
import com.tst.service.models.RatesAndPrices.{CabinPrice, Rate}
import com.tst.service.services.{PriceService, PromotionService}

object Main extends IOApp { server =>

  override def run(args: List[String]): IO[ExitCode] = (for {
    _ <- PriceService.getBestGroupPrices(rateGroups, cabinPrices).map(println)
    _ <- PromotionService.allCombinablePromotions(promotions).map(println)
    _ <- PromotionService.combinablePromotions(P1, promotions).map(println)
    _ <- PromotionService.combinablePromotions(P3, promotions).map(println)
  } yield ()).value.as(ExitCode.Success)

  private val rateGroups = Seq(Rate(M1, Military), Rate(M2, Military), Rate(S1, Senior), Rate(S2, Senior))

  private val cabinPrices = Seq(
    CabinPrice(CA, M1, 200.00),
    CabinPrice(CA, M2, 250.00),
    CabinPrice(CA, S1, 225.00),
    CabinPrice(CA, S2, 260.00),
    CabinPrice(CB, M1, 230.00),
    CabinPrice(CB, M2, 260.00),
    CabinPrice(CB, S1, 245.00),
    CabinPrice(CB, S2, 270.00)
  )

  private val promotions = Seq(
    Promotion(P1, Seq(P3)),
    Promotion(P2, Seq(P4, P5)),
    Promotion(P3, Seq(P1)),
    Promotion(P4, Seq(P2)),
    Promotion(P5, Seq(P2))
  )

}
