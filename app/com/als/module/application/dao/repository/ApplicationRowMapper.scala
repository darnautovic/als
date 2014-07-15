package com.als.module.application.dao.repository

import anorm.SqlParser._
import anorm.~
import com.als.domain.Application

object ApplicationRowMapper {
  val full = {
    get[Long]("id") ~
      get[String]("name") ~
      get[String]("version") ~
      get[Long]("user_id") ~
      get[Option[String]]("public_key") ~
      get[Option[String]]("private_key")map {
      case
        id ~
          name ~
          version ~
          userId ~
          publicKey ~
          privateKey
      =>
        Application.Full(
          id,
          name,
          version,
          publicKey,
          privateKey
        )
    }
  }
}
