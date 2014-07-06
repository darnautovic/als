package module.application.dao.repository

import anorm.SqlParser._
import anorm.~
import domain.Application

object ApplicationRowMapper {
  val full = {
    get[Long]("id") ~
      get[String]("name") ~
      get[String]("version") ~
      get[Long]("user_id") map {
      case
        id ~
          name ~
          version ~
          userId
      =>
        Application.Full(
          id,
          name,
          version,
          userId
        )
    }
  }
}
