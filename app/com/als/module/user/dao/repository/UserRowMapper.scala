package com.als.module.user.dao.repository

import com.als.domain.User
import anorm.SqlParser._
import anorm.~

object UserRowMapper
{
  val full = {
    get[Long]("id") ~
      get[String]("username") ~
      get[String]("password") ~
      get[String]("first_name") ~
      get[String]("last_name") ~
      get[String]("email") map {
      case
        id ~
          username  ~
          password  ~
          firstName ~
          lastName  ~
          email
      =>
        User.Full(
          id,
          username,
          password,
          firstName,
          lastName,
          email
        )
    }
  }
}
