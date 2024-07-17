package com.tst.service

import cats.data.EitherT
import cats.data.EitherT.right
import cats.effect.{ExitCode, IO, IOApp}
import com.tst.service.models.RatesAndPrices.{BestGroupPrice, CabinPrice, Rate}

object Main extends IOApp { server =>

  //  private implicit val config: Config = Config.loadConfig

  override def run(args: List[String]): IO[ExitCode] =
    IO.println("Hello World").as(ExitCode.Success)

  def getBestGroupPrices(rates: Seq[Rate], prices: Seq[CabinPrice]): EitherT[IO, Exception, Seq[BestGroupPrice]] =
    right(IO.delay {
      prices
        .groupBy(_.rateCode)
        .flatMap { byRate =>
          byRate._2
            .groupBy(_.cabinCode)
            .map(_._2.minBy(_.price))
            .map(best => BestGroupPrice(best.cabinCode, best.rateCode, best.price, byRate._1))
        }
        .toSeq
    })

}
