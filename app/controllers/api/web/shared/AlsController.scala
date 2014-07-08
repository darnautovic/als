package controllers.api.web.shared

import play.api.mvc._
import play.api.Logger
import com.als.domain.authentication.{Session => AlsSession}

class AlsController extends Controller with Secured
{
  def onUnauthorized(request: RequestHeader, error: Option[String]): Result =
  {
    Logger.info( "Unauthorized request on path: " + request.method+ "/" + request.uri )
    Redirect(controllers.api.web.user.authentication.routes.AuthenticationController.showLoginPage())
  }
  def onException(exception: Option[Exception] = None): Result =
  {
    Redirect(controllers.api.web.user.authentication.routes.AuthenticationController.showLoginPage())
  }
  def translateToPlayInput(inputMap : Map[String, Seq[String]], args: Seq[String]) = {
    inputMap.flatMap ({
      case (key, value) if (value.length == 1 && !args.contains(key)) => {
        Map(key->value.head)
      }
      case (key, value) if (value.length == 1 && args.contains(key)) => {
        Map(key+"[0]"->value.head)
      }
      case (key, value) if value.length > 1 => {
        value.zipWithIndex.map {
          case (value2, index) => (key +"[" + index+"]", value2)
        }
      }
    })
  }

  def onExpired(request: RequestHeader, session: AlsSession): Result = {
    sessionService.deleteBySessionId(session.sessionId)
    Logger.info( "Session with id " + session.sessionId + " expired at: " +  session.expiresOn)
    Redirect(controllers.api.web.user.authentication.routes.AuthenticationController.login())
  }
}
