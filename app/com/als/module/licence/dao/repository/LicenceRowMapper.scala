package com.als.module.licence.dao.repository

import java.util.Date

import play.api.Play.current
import com.als.domain.Licence
import anorm.SqlParser._
import anorm.~
import com.als.shared.utils.date.DateUtils

object LicenceRowMapper {
  val full = {
    get[Long]("id") ~
      get[Long]("serial_id") ~
      get[Date]("created_on") ~
      get[Boolean]("active") ~
      get[String]("licence_hash") ~
      get[String]("signed_hash") map {
      case
        id ~
          serialNumberId ~
          createdOn ~
          active ~
          licenceHash ~
          signedHash
      =>
        Licence.Full(
          id,
          serialNumberId,
          DateUtils.javaDateToJodaDateTime(createdOn),
          active,
          licenceHash,
          signedHash
        )
    }
  }
}
