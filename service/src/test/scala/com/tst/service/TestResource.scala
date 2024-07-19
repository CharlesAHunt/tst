package com.tst.service

import cats.effect.{IO, Resource}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import weaver.IOSuite

class TestResource extends IOSuite with AnyWordSpecLike with Matchers with BeforeAndAfterAll {

  type Res = Unit
  override def sharedResource: Resource[IO, Res] = resources

  def resources: Resource[IO, Unit] = Resource.unit

}
