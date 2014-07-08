package com.als.module.authentication.session.dao

import com.als.domain.authentication.{AuthenticatedUser, Session}
import org.joda.time.DateTime

trait SessionDao
{
  def insert(item: Session)
  def findBySessionId(sessionId: String): Option[Session]
  def deleteBySessionId(sessionId: String)
  def deleteExpiredSessions()
  def updateExpirationDate(sessionId: String, expirationDate: DateTime)
  def setDataForSessionId(sessionId: String, user: AuthenticatedUser)
}
