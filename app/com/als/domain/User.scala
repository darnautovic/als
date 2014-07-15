package com.als.domain

object User
{
  case class Full
  (
    id        : Long,
    username  : String,
    password  : String,
    firstName : String,
    lastName  : String,
    email     : String
  )

   case class Create
  (
    firstName : String,
    lastName  : String,
    email     : String,
    username  : String,
    password  : String
  )

  case class Edit
  (
    id: Long,
    password       : Option[String],
    retypedPassword: Option[String],
    firstName : String,
    lastName  : String,
    email     : String
  )
}
