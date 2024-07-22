package com.tst

import cats.data.EitherT
import cats.data.EitherT.{leftT, rightT}
import cats.effect.IO
import com.tst.service.models.RatesAndPrices.BestGroupPrice
import com.tst.service.models.{NotFound, TSTError}

package object service {

  type EitherSeq[A] = EitherT[IO, TSTError, Seq[A]]
  def rightTST[A](a: A): EitherT[IO, TSTError, A] = rightT[IO, TSTError](a)

  val notFound: EitherT[IO, TSTError, BestGroupPrice] = leftT[IO, BestGroupPrice](NotFound)

  // Rate Groups
  val Military = "Military"
  val Senior = "Senior"

  // Rate Codes
  val M1 = "M1"
  val M2 = "M2"
  val S1 = "S1"
  val S2 = "S2"

  // Cabins
  val CA = "CA"
  val CB = "CB"

  // Promotions
  val P1 = "P1"
  val P2 = "P2"
  val P3 = "P3"
  val P4 = "P4"
  val P5 = "P5"
}
