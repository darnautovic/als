package controllers.api.web.user

import com.als.module.registry.ServiceRegistry
import models.user.models.user.authentication.{Login, LoginForm, LoginModel}
import models.user.{UserCreateForm, UserCreateModel}
import play.api.Play.current
import play.api.mvc.{Controller, Action}

object UserController extends Controller  {

  private val authenticationService = ServiceRegistry.webAuthenticationService
  private val userService           = ServiceRegistry.userService

  def createPage = Action
  {
    implicit request =>

      val formWithDefaultValues =  UserCreateForm.form

      Ok(views.html.user.create(UserCreateModel(formWithDefaultValues)))
  }

  def create = Action
  {
    implicit request =>
      val form = UserCreateForm.form.bindFromRequest()(request)
      val formWithDefaultValues =  LoginForm.form

      form.fold(
        formWithErrors =>
        {
          BadRequest(views.html.user.create(UserCreateModel(form)))
        },
        user =>
        {
          val generatedId = userService.create(user)

          Ok(views.html.user.login(LoginModel(formWithDefaultValues)))
        })
  }
}
