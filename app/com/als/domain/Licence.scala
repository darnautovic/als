package com.als.domain

import org.joda.time.DateTime

object Licence
{
  case class Full
  (
    id             :Long,
    serialNumberId :Long,
    createdOn      :DateTime,
    active         :Boolean,
    keys           :Keys,
    licenceHash    :String
  )

  case class Create
  (
    serialNumberId :Long,
    createdOn      :DateTime,
    active         :Boolean,
    keys           :Keys,
    licenceHash    :String
  )

  case class Edit
  (
    id             :Long,
    serialNumberId :Long,
    createdOn      :DateTime,
    active         :Boolean,
    keys           :Keys,
    licenceHash    :String
  )

  case class Keys
  (
    publicKey   :String,
    privateKey  :String
  )
}

