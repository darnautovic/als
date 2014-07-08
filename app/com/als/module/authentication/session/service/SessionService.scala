package com.als.module.authentication.session.service

import com.als.domain.authentication.{Session, AuthenticatedUser}

trait SessionService {
  def create(user: AuthenticatedUser): Session
  def getBySessionId(sessionId: String): Option[Session]
  def deleteBySessionId(sessionId: String)
  def increaseExpiration(sessionId: String)
  def setDataForSessionId(sessionId: String, user: AuthenticatedUser)
}
