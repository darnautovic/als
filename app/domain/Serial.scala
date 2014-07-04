package domain

import org.joda.time.DateTime

case class Serial
(
  id            : Long,
  applicationId : Long,
  serialNumber  : String,
  createdOn     : DateTime
)

