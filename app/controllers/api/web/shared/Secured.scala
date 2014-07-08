package controllers.api.web.shared

import com.als.module.registry.ServiceRegistry
import play.api.mvc._
import play.api.data.Form
import org.joda.time.DateTime
import play.api.Logger
import com.als.domain.authentication.{Session => AlsSession}
import org.joda.time.format.DateTimeFormatter
import com.als.shared.utils.date.DateUtils._

trait Secured {

  private final val CACHE_CONTROL_DEFAULT_HEADER    = "Cache-Control"    -> "no-cache, no-store, must-revalidate"
  private final val PRAGMA_DEFAULT_HEADER           = "Pragma"           -> "no-cache"
  private final val EXPIRES_DEFAULT_HEADER          = "Expires"          -> "0"
  private final val X_FRAME_OPTIONS_DEFAULT_HEADER  = "X-Frame-Options"  -> "deny"
  private def IS_EXPIRED_HEADER(session: Option[AlsSession]) = {
    if(session.isDefined) { Some("Is-Expired" -> session.get.isExpired.toString) }
    else                  None
  }

  val sessionService = ServiceRegistry.sessionService

  def onUnauthorized(request: RequestHeader, error: Option[String]): Result

  def onException(exception: Option[Exception] = None): Result
  def onExpired(request: RequestHeader, session: AlsSession): Result

  def onValid[A](block: RequestWithSession[A] => Result, request: Request[A], session: AlsSession): Result = {
    Logger.info( "Request by " + session.user.username + " path: " + request.method+ "/" + request.uri + " performed")

    sessionService.increaseExpiration(session.sessionId)

    block(RequestWithSession(request, session))
  }


  def AuthenticatedActionWithoutForcePasswordChangeCheck[A](f: RequestWithSession[AnyContent] => Result): Action[AnyContent] = {
    Action {
      implicit request =>
        val session = getSession(request)
        val result = session match {
          case Some(session: AlsSession) if session.isValid => f(RequestWithSession(request, session))
          case Some(session: AlsSession) if session.isExpired => onExpired(request,session)
          case _ => onUnauthorized(request, None)
        }

        addDefaultResponseHeaders(result, session)
    }
  }

  private def AuthenticatedActionDevelopment[A](f: RequestWithSession[AnyContent] => Result): Action[AnyContent] = {
    Action {
      implicit request =>
        val session = getSession(request)
        val result = session match {
          case Some(session: AlsSession) if session.isValid => onValid(f, request, session)
          case Some(session: AlsSession) if session.isExpired => onExpired(request,session)
          case _ => onUnauthorized(request, None)
        }

        addDefaultResponseHeaders(result, session)
    }
  }

  def AuthenticatedAction[A](f: RequestWithSession[AnyContent] => Result): Action[AnyContent] = AuthenticatedActionDevelopment(f)

  private def fullMatch(required: Seq[Long], existing: Set[Long]) = {
    required.forall( r => existing.contains(r))
  }

  def AuthenticatedAction[A](permissions: Long*)(f: RequestWithSession[AnyContent] => Result): Action[AnyContent] = {
    Action {
      implicit request =>
        val session = getSession(request)

        val result = session match {
          case Some(session: AlsSession) if session.isValid                                          => onValid(f, request, session)
          case Some(session: AlsSession) if session.isExpired                                        => onExpired(request,session)
          case _                                                                                    => onUnauthorized(request, None)
        }

        addDefaultResponseHeaders(result, session)
    }
  }


  def LoggedAction[A](f: Request[AnyContent] => Result): Action[AnyContent] = {
    Action {
      implicit request =>
        val session = getSession(request)

        Logger.info( "Anonymous request: " + request.method+ "/" + request.uri + " performed from: " + request.remoteAddress)
        val result = f (request)

        addDefaultResponseHeaders(result, session)
    }
  }


  def AuthenticatedFormAction[F,A]( form              : Form[F],
                                    //formValidator     :
                                    badRequestHandler : Form[F] => RequestWithSession[AnyContent] => Result,
                                    goodRequestHandler: F => RequestWithSession[AnyContent] => Result ): Action[AnyContent] = {
    AuthenticatedAction{
      implicit requestWithSession =>
        form.bindFromRequest()(requestWithSession.request).fold (
          badRequestHandler(_)(requestWithSession),
          goodRequestHandler(_)(requestWithSession)
        )
    }
  }

  private def getSession(request: RequestHeader): Option[AlsSession] = {
    val sessionIdCookie: Option[Cookie] = request.cookies.get(SessionToken.ALS.getTokenName)

    if (sessionIdCookie.isDefined) {
      sessionService.getBySessionId(sessionIdCookie.get.value)
    }
    else {
      Option.empty
    }
  }

  private def addDefaultResponseHeaders(result: Result, session: Option[AlsSession]): Result =
  {
    val resultWithDefaultHeaders = result.withHeaders(
      CACHE_CONTROL_DEFAULT_HEADER,
      PRAGMA_DEFAULT_HEADER,
      EXPIRES_DEFAULT_HEADER,
      X_FRAME_OPTIONS_DEFAULT_HEADER
    )

    val isExpiredHeader = IS_EXPIRED_HEADER(session)
    if(isExpiredHeader.isDefined) {
      return resultWithDefaultHeaders.withHeaders(isExpiredHeader.get)
    } else {
      return resultWithDefaultHeaders
    }
  }
}

case class RequestWithSession[A](request: Request[A], session: AlsSession)
{
  def formatDateAndTime(value: DateTime) =
  {

    dateTimeFormatter.print(value)
  }

  def formatDateAndTimeWithCustomFormatter(value: DateTime, formatter: DateTimeFormatter) =
  {

    formatter.print(value)
  }
}
