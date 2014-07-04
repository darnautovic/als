package domain

import org.joda.time.DateTime

case class Licence
(
  id            :Long,
  serialId      :Long,
  createdOn     :DateTime,
  active        :Boolean,
  keys          :Keys,
  licenceHash   :String
)

case class Keys
(
  publicKey   :String,
  privateKey  :String
)