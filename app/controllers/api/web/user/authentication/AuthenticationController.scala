package controllers.api.web.user.authentication

import play.api.Play._
import com.als.domain.authentication.LoginCredentials
import com.als.module.registry.ServiceRegistry
import controllers.api.web.shared.{SessionToken, AlsController}
import models.user.models.user.authentication.{LoginForm, LoginModel}
import play.api.mvc._
import views.html

object AuthenticationController extends AlsController
{
  private val authenticationService = ServiceRegistry.webAuthenticationService

  def showLoginPage = Action
  {
    Ok(html.user.login(LoginModel(LoginForm.form)))
  }

  def login = Action
  {
    implicit request =>
      val form = LoginForm.form.bindFromRequest
      form.fold(
        formWithErrors =>
        {
          BadRequest(html.user.login(LoginModel(formWithErrors)))
        },
        credentials =>
        {
          val session = authenticationService.login(
            LoginCredentials(credentials.username, credentials.password)
          )
          Redirect(controllers.api.web.routes.DashboardController.showUserDashboard).withCookies(
            Cookie(SessionToken.ALS.getTokenName, session.sessionId)
          )
        }
      )
  }

  def logout = AuthenticatedActionWithoutForcePasswordChangeCheck
  {
    implicit requestWithSession =>

      authenticationService.logout(requestWithSession.session.sessionId)

      Redirect(controllers.api.web.user.authentication.routes.AuthenticationController.showLoginPage()).discardingCookies(DiscardingCookie((SessionToken.ALS.getTokenName)))
  }
}

