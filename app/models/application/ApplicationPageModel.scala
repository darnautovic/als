package models.application

import com.als.domain.{Licence, Serial, Application}

case class ApplicationPageModel
(
 application :  Option[Application.Full],
 serials      : Seq[Serial.Full],
 licenses     : Seq[Licence.Full]
)
