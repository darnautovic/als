package domain.authentication

import org.joda.time.DateTime
import play.api.libs.json.Json
import shared.utils.date.DateUtils


case class Session
(
  sessionId: String,
  createdOn: DateTime,
  expiresOn: DateTime,
  user: AuthenticatedUser
  )
{
  def isValid: Boolean =
  {
    !isExpired
  }

  def isExpired: Boolean =
  {
    DateUtils.nowDateTimeUTC().isAfter(expiresOn)
  }
}

case class AuthenticatedUser
(
  id: Long,
  username: String,
  firstName: String,
  lastName: String
)

object AuthenticatedUserJsonMapper
{
  def toJson(value: AuthenticatedUser): String =
  {
    implicit val authenticatedUserJsonWrites  = Json.writes[AuthenticatedUser]

    Json.toJson(value).toString
  }

  def fromJson(value: String): AuthenticatedUser =
  {
    implicit val authenticatedUserJsonReads = Json.reads[AuthenticatedUser]

    Json.parse(value).as[AuthenticatedUser]
  }
}