package com.tst.service

import cats.effect.{ExitCode, IO, IOApp}
import com.tst.service.models.Promotion
import com.tst.service.models.RatesAndPrices.{CabinPrice, Rate}
import com.tst.service.services.{PriceService, PromotionService}
import InputData.*

import scala.Console.CYAN

object Main extends IOApp { server =>

  override def run(args: List[String]): IO[ExitCode] = (for {
    _ <- PriceService.getBestGroupPrices(rateGroups, cabinPrices).map(r => println(s"$CYAN $r"))
    _ <- PromotionService.allCombinablePromotions(promotions).map(r => println(s"$CYAN $r"))
    _ <- PromotionService.combinablePromotions(promotions, P1).map(r => println(s"$CYAN $r"))
    _ <- PromotionService.combinablePromotions(promotions, P3).map(r => println(s"$CYAN $r"))
  } yield ()).value.as(ExitCode.Success)

}

object InputData {
  val rateGroups: Seq[Rate] = Seq(Rate(M1, Military), Rate(M2, Military), Rate(S1, Senior), Rate(S2, Senior))

  val cabinPrices: Seq[CabinPrice] = Seq(
    CabinPrice(CA, M1, 200.00),
    CabinPrice(CA, M2, 250.00),
    CabinPrice(CA, S1, 225.00),
    CabinPrice(CA, S2, 260.00),
    CabinPrice(CB, M1, 230.00),
    CabinPrice(CB, M2, 260.00),
    CabinPrice(CB, S1, 245.00),
    CabinPrice(CB, S2, 270.00)
  )

  val promotions: Seq[Promotion] = Seq(
    Promotion(P1, Seq(P3)),
    Promotion(P2, Seq(P4, P5)),
    Promotion(P3, Seq(P1)),
    Promotion(P4, Seq(P2)),
    Promotion(P5, Seq(P2))
  )
}
