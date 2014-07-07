package module.authentication.session.service.implementation

import anorm.SqlParser._
import anorm.~

import java.util.Date
import domain.authentication.{AuthenticatedUserJsonMapper, Session}
import shared.utils.date.DateUtils

object SessionRowMapper {
  val mapping = {
    get[String]("session_id") ~
      get[Date]("created_on") ~
      get[Date]("expires_on") ~
      get[String]("data") map {
      case
        sessionId ~
          createdOn ~
          expiresOn ~
          data
      =>
        Session(
          sessionId,
          DateUtils.javaDateToJodaDateTime(createdOn),
          DateUtils.javaDateToJodaDateTime(expiresOn),
          AuthenticatedUserJsonMapper.fromJson(data)
        )
    }
  }


}
