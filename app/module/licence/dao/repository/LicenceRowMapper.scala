package module.licence.dao.repository

import java.util.Date

import play.api.Play.current
import domain.Licence.Keys
import domain.Licence
import anorm.SqlParser._
import anorm.~
import shared.utils.date.DateUtils

object LicenceRowMapper {
  val full = {
    get[Long]("id") ~
      get[Long]("serial_id") ~
      get[Date]("created_on") ~
      get[Boolean]("active") ~
      get[String]("licence_hash") ~
      get[String]("public_key") ~
      get[String]("private_key") map {
      case
        id ~
          serialNumberId ~
          createdOn ~
          active ~
          licenceHash ~
          publicKey ~
          privateKey
      =>
        Licence.Full(
          id,
          serialNumberId,
          DateUtils.javaDateToJodaDateTime(createdOn),
          active,
          Keys(publicKey, privateKey),
          licenceHash
        )
    }
  }
}
