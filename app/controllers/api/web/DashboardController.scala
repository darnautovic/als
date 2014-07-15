package controllers.api.web

import controllers.api.web.shared.AlsController
import views._

object DashboardController extends AlsController {

  def showUserDashboard = AuthenticatedAction {
    implicit requestWithSession =>

      Ok(html.dashboard(requestWithSession))
  }
}
