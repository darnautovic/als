package models.user

import com.als.domain.User
import play.api.data._
import play.api.data.Forms._

case class UserCreateModel
(
  form: Form[User.Create]
)

object UserCreateForm
{
  val form: Form[User.Create] = Form(
    mapping(
    "firstName"-> nonEmptyText,
    "lastName" -> nonEmptyText,
    "email"    -> nonEmptyText,
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
    )
    (User.Create.apply)(User.Create.unapply)
  )
}
