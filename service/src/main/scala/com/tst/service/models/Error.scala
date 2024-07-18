package com.tst.service.models

sealed trait TSTError

final case class NotFound() extends TSTError
