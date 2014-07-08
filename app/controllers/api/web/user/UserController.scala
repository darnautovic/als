//package controllers.api.web.user
//
//import com.als.module.registry.ServiceRegistry
//import play.api.Play.current
//import play.api.mvc.Action
//
//object UserController {
//
//  private val authenticationService = ServiceRegistry.webAuthenticationService
//
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
