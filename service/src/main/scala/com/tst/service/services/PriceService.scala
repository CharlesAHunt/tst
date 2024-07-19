package com.tst.service.services

import cats.data.EitherT
import cats.data.EitherT.rightT
import cats.effect.IO
import cats.implicits.{catsSyntaxEq, toTraverseOps}
import com.tst.service.{EitherSeq, notFound}
import com.tst.service.models.RatesAndPrices.{BestGroupPrice, CabinPrice, Rate}
import com.tst.service.models.{NotFound, TSTError}

object PriceService {

  /**
   * Calculates the best group prices for a cabin and rate group
   *
   * @param rates all rates and rate groups
   * @param prices all cabin prices with a specific rate code
   * @return the lowest cabin price for any rate group and cabin
   */
  def getBestGroupPrices(rates: Seq[Rate], prices: Seq[CabinPrice]): EitherSeq[BestGroupPrice] = {
    implicit val implicitRates: Seq[Rate] = rates
    for {
      rateGroupPrices <- prices.toList.traverse(attachRateGroup)
      groupedByCabin <- rightT[IO, TSTError](rateGroupPrices.groupBy(rgp => (rgp.rateGroup, rgp.cabinCode)))
      result <- rightT[IO, TSTError](groupedByCabin.map(_._2.minBy(_.price)).toSeq)
    } yield result
  }

  private def attachRateGroup(price: CabinPrice)(implicit rates: Seq[Rate]): EitherT[IO, NotFound, BestGroupPrice] =
    rates
      .find(_.rateCode === price.rateCode)
      .map(_.rateGroup)
      .fold(notFound)(group => rightT(BestGroupPrice(price.cabinCode, price.rateCode, price.price, group)))

}
