package module.authentication.session.service.implementation

import java.util.UUID

import domain.authentication.{Session, AuthenticatedUser}
import module.authentication.session.dao.SessionDao
import shared.utils.crypto.HashUtils
import shared.utils.date.DateUtils

class SessionServiceImpl (sessionDao :SessionDao) {
  private val EXPIRATION_PERIOD_IN_MINUTES = 10

  private def generateSessionId: String =
  {
    UUID.randomUUID().toString
  }

  def createWithData(user: AuthenticatedUser): Session =
  {

    val sessionId = generateSessionId

    val session = Session(
      sessionId = sessionId,
      createdOn = DateUtils.nowDateTimeUTC(),
      expiresOn = DateUtils.nowDateTimeUTC().plusMinutes(EXPIRATION_PERIOD_IN_MINUTES),
      user      = user
    )

    val sessionWithHashedSessionId = session.copy(sessionId = HashUtils.sha1(sessionId))

    sessionDao.insert(sessionWithHashedSessionId)

    session
  }

  def getBySessionId(sessionId: String): Option[Session] =
  {
    val hashedSessionId = HashUtils.sha1(sessionId)

    val sessionWithHashedId = sessionDao.findBySessionId(hashedSessionId)

    sessionWithHashedId.map(_.copy(sessionId = sessionId))
  }

  def deleteBySessionId(sessionId: String)
  {
    val hashedSessionId = HashUtils.sha1(sessionId)

    sessionDao.deleteBySessionId(hashedSessionId)
  }
  def deleteExpiredSessions()
  {
    sessionDao.deleteExpiredSessions()
  }

  def setDataForSessionId(sessionId: String, user: AuthenticatedUser)
  {
    val hashedSessionId = HashUtils.sha1(sessionId)

    sessionDao.setDataForSessionId(hashedSessionId, user)
  }

  def increaseSessionExpiration(sessionId: String) =
  {
    val expirationDate = DateUtils.nowDateTimeUTC().plusMinutes(EXPIRATION_PERIOD_IN_MINUTES)
    val hashedSessionId = HashUtils.sha1(sessionId)

    sessionDao.updateExpirationDate(hashedSessionId, expirationDate)
  }
}
