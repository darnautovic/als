//package controllers.api.web.user
//
//import play.api.Play.current
//
//object UserController {
//
//  def showLoginPage = Action
//  {
//    Ok(html.user.authentication.login(LoginModel(LoginForm.form, organisationName)))
//  }
//
//  def login = Action
//  {
//    implicit request =>
//
//      val configuration = play.api.Play.configuration
//
//      val form = LoginForm.form.bindFromRequest
//
//      form.fold(
//        formWithErrors  =>
//        {
//          BadRequest(html.user.authentication.login(LoginModel(formWithErrors,organisationName)))
//        },
//        credentials     =>
//        {
//          val session = authenticationService.login(
//            Credentials(credentials.username, credentials.password)
//          )
//
//
//          Redirect(controllers.dashboard.routes.DashboardController.showUserDashboard()).withCookies(
//            Cookie(InstafinCookies.SESSION_TOKEN, session.sessionId, secure = sessionSecureFlag)
//          )
//        }
//      )
//  }
//
//  def logout = AuthenticatedActionWithoutForcePasswordChangeCheck
//  {
//    implicit requestWithSession =>
//      authenticationService.logout(requestWithSession.session.sessionId)
//
//      Redirect(controllers.user.authentication.routes.AuthenticationController.showLoginPage()).discardingCookies(DiscardingCookie(InstafinCookies.SESSION_TOKEN))
//  }
//}
