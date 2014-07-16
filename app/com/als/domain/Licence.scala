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
    licenceHash    :String,
    signedHash     :String
  )

  case class Create
  (
    serialNumberId :Long,
    clientId       :Long,
    createdOn      :DateTime,
    active         :Boolean,
    licenceHash    :String,
    signedHash     :String
  )

  case class Edit
  (
    id             :Long,
    serialNumberId :Long,
    createdOn      :DateTime,
    active         :Boolean,
    licenceHash    :String
  )


}

