package com.tst.service.services

import cats.implicits.catsSyntaxEq
import com.tst.service.{EitherSeq, rightTST}
import com.tst.service.models.Promotions.{Promotion, PromotionCombo}

import scala.annotation.tailrec

object PromotionService {

  /**
   * Calculates all possible promotion combinations
   *
   * @param allPromos All promotions available
   * @return all combinations of promotions
   */
  def allCombinablePromotions(allPromos: Seq[Promotion]): EitherSeq[PromotionCombo] =
    for {
      possibleCombos <- rightTST(allPromos.map(promo => possibleCombos(allPromos, promo)))
      fromRight <- rightTST(possibleCombos.map(promo => PromotionCombo(buildCombos(promo.reverse).sorted)))
      fromLeft <- rightTST(possibleCombos.map(promo => PromotionCombo(buildCombos(promo).sorted)))
      distinct <- rightTST((fromLeft ++ fromRight).distinctBy(_.promotionCodes))
      result <- rightTST(distinct.filter(_.promotionCodes.lengthIs > 1))
    } yield result

  /**
   * Calculates all combinable promotions with a specific promoCode
   *
   * @param promoCode specific code to find all combinable promotions
   * @param allPromos all available promotions
   * @return all combinations of promotions with the given promoCode
   */
  def combinablePromotions(allPromos: Seq[Promotion], promoCode: String): EitherSeq[PromotionCombo] = for {
    all <- allCombinablePromotions(allPromos)
    result <- rightTST(all.filter(_.promotionCodes.contains(promoCode)))
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
