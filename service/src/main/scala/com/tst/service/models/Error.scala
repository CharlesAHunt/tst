package com.tst.service.models

sealed trait TSTError

case object NotFound extends TSTError
