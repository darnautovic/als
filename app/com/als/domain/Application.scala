package com.als.domain

object Application
{
  case class Full
  (
    id      :Long,
    name    :String,
    version :String,
    userId  :Long
  )

  case class Create
  (
    name    :String,
    version :String,
    userId  :Long
  )

  case class Edit
  (
    name    :String,
    version :String,
    userId  :Long
    )
}