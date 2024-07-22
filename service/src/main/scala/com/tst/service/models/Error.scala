package com.tst.service.models

sealed trait TSTError

final case object NotFound extends TSTError
