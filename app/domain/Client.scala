package domain

object Client
{
  case class Full
  (
    id         : Long,
    name       :String,
    lastName   :String,
    company    :Option[String]
  )

  case class Create
  (
    name       :String,
    lastName   :String,
    company    :Option[String]
  )

  case class Edit
  (
    id         : Long,
    name       :String,
    lastName   :String,
    company    :Option[String]
  )
}



