package com.tst.service.services

import cats.data.EitherT
import cats.data.EitherT.{leftT, rightT}
import cats.effect.IO
import cats.implicits.{catsSyntaxEq, toTraverseOps}
import com.tst.service.models.RatesAndPrices.{BestGroupPrice, CabinPrice, Rate}
import com.tst.service.models.{NotFound, TSTError}

object PriceService {

  def getBestGroupPrices(rates: Seq[Rate], prices: Seq[CabinPrice]): EitherT[IO, TSTError, Seq[BestGroupPrice]] = {
    implicit val implicitRates: Seq[Rate] = rates
    for {
      rateGroupPrices <- prices.toList.traverse(attachRateGroup)
      groupedByCabin <- rightT[IO, TSTError](rateGroupPrices.groupBy(rgp => (rgp.rateGroup, rgp.cabinCode)))
      result <- rightT[IO, TSTError](groupedByCabin.map(_._2.minBy(_.price)).toSeq)
    } yield result
  }

  private def attachRateGroup(
    cabinPrice: CabinPrice
  )(implicit rates: Seq[Rate]): EitherT[IO, NotFound, BestGroupPrice] =
    rates
      .find(_.rateCode === cabinPrice.rateCode)
      .map(_.rateGroup)
      .fold(leftT[IO, BestGroupPrice](NotFound()))(group =>
        rightT[IO, NotFound](
          BestGroupPrice(cabinPrice.cabinCode, cabinPrice.rateCode, cabinPrice.price, group)
        )
      )

}
