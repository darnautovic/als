package com.als.domain

import org.joda.time.DateTime

object Serial
{
  case class Full
  (
    id            : Long,
    applicationId : Long,
    serialNumber  : String,
    createdOn     : DateTime
  )

  case class Create
  (
    applicationId : Long,
    serialNumber  : String,
    createdOn     : DateTime
  )
}


