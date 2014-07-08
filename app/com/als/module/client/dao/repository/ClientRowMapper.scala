package com.als.module.client.dao.repository

import com.als.domain.Client
import anorm.SqlParser._
import anorm.~

object ClientRowMapper {
  val full = {
    get[Long]("id") ~
      get[String]("name") ~
      get[String]("lastName") ~
      get[Option[String]]("company") map {
      case
        id ~
          name ~
          lastName ~
          company
      =>
        Client.Full(
          id,
          name,
          lastName,
          company
        )
    }
  }
}
