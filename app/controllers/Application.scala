package controllers

import controllers.api.web.shared.AlsController

object Application extends AlsController {

  def index = AuthenticatedAction {
    implicit requestWithSession =>

        Ok(views.html.index("LoggedIn"))
  }
}