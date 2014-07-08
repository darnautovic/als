package com.als.shared.utils.crypto

import play.api.libs.Codecs

object HashUtils {
  val salt = "If:AxwupMx%Q;|WImrbng_t!#a,[{*t+&lF`+1EV,PdIFTue`nO~kZ4,wz!yXMbF"

  val recalculateTimes = 10

  def sha1(value: String): String = {
    var result = value

    for (i <- 1 to recalculateTimes) {
      result = Codecs.sha1(salt + value)
    }

    result
  }
}