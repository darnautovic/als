package domain

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
    username  : String,
    password  : String,
    firstName : String,
    lastName  : String,
    email     : String
  )

  case class Edit
  (
    id: Long,
    password  : String,
    firstName : String,
    lastName  : String,
    email     : String
  )
}
