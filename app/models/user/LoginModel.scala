package models.user

package models.user.authentication

import com.als.domain.authentication.LoginCredentials
import com.als.module.registry.ServiceRegistry
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._


case class LoginModel(form: Form[Login])

case class Login
(
  username: String,
  password: String
  )

object LoginForm
{
  val authenticationService = ServiceRegistry.webAuthenticationService

  val form: Form[Login] = Form(
    mapping(
      "username" -> nonEmptyText(),
      "password" -> nonEmptyText()
    )
      (Login.apply)
      (Login.unapply)
      verifying("Wrong username or password", credentials => authenticationService.canLogin(LoginCredentials(credentials.username, credentials.password)))
  )
}