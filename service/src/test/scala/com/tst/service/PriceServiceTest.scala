package com.tst.service

import cats.effect.{IO, Resource}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import weaver.IOSuite

object PriceServiceTest extends IOSuite with AnyWordSpecLike with Matchers with BeforeAndAfterAll {

  test("Test best price for cabin and rate group") { resources =>
    IO.pure(expect(1 == 1))

  }

  type Res = Unit
  override def sharedResource: Resource[IO, Res] = resources

  def resources: Resource[IO, Unit] = Resource.unit
}
