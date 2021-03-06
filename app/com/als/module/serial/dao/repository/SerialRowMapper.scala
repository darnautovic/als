package com.als.module.serial.dao.repository

import java.util.Date

import play.api.Play.current
import anorm.SqlParser._
import anorm.~
import com.als.domain.Serial
import com.als.shared.utils.date.DateUtils

object SerialRowMapper {
  val full = {
    get[Long]("serials.id") ~
      get[Long]("application_id") ~
      get[String]("serial_number") ~
      get[Date]("created_on") map {
      case
        id ~
          applicationId ~
          serialNumber ~
          createdOn
      =>
        Serial.Full(
          id,
          applicationId,
          serialNumber,
          DateUtils.javaDateToJodaDateTime(createdOn)
        )
    }
  }
}

