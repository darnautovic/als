package com.als.domain

object Application
{
  case class Full
  (
    id      :Long,
    name    :String,
    version :String,
    publicKey :Option[String],
    privateKey:Option[String]
 )

  case class Create
  (
    name      :String,
    version   :String,
    publicKey :Option[String],
    privateKey:Option[String]
  )

  case class Edit
  (
    name    :String,
    version :String,
    userId  :Long
  )
}