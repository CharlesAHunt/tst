package com.tst.service.services

import cats.data.EitherT.rightT
import cats.effect.IO
import cats.implicits.catsSyntaxEq
import com.tst.service.EitherSeq
import com.tst.service.models.{Promotion, PromotionCombo, TSTError}

import scala.annotation.tailrec

object PromotionService {

  /**
   * Calculates all possible promotions
   *
   * @param allPromos All promotions available
   * @return all combinations of promotions
   */
  def allCombinablePromotions(allPromos: Seq[Promotion]): EitherSeq[PromotionCombo] =
    rightT {
      val possibleCombinations = allPromos.map(promo => possibleCombos(allPromos, promo))
      (possibleCombinations.map(promo => PromotionCombo(buildCombos(promo.reverse).sorted)) ++ possibleCombinations
        .map(promo => PromotionCombo(buildCombos(promo).sorted)))
        .distinctBy(_.promotionCodes)
    }

  /**
   * Calculates all combinable promotions with a specific promoCode
   *
   * @param promoCode specific code to find all combinable promotions
   * @param allPromos all available promotions
   * @return all combinations of promotions with the given promoCode
   */
  def combinablePromotions(allPromos: Seq[Promotion], promoCode: String): EitherSeq[PromotionCombo] = for {
    all <- allCombinablePromotions(allPromos)
    result <- rightT[IO, TSTError](all.filter(_.promotionCodes.contains(promoCode)))
  } yield result

  private def possibleCombos(allPromos: Seq[Promotion], promo: Promotion): Seq[Promotion] = allPromos
    .map(_.code)
    .diff(promo.notCombinableWith)
    .flatMap(p => allPromos.find(_.code === p))

  @tailrec
  private def buildCombos(possible: Seq[Promotion], soFar: Seq[String] = Seq.empty): Seq[String] =
    possible.toList match {
      case Nil          => soFar
      case head :: Nil  => soFar.:+(head.code)
      case head :: tail => buildCombos(possibleCombos(tail, head), soFar.:+(head.code))
    }
}
