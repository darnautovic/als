package com.als.module.authentication.web.service.implementation

import com.als.domain.authentication.{Session, AuthenticatedUser, LoginCredentials}
import com.als.module.authentication.session.service.SessionService
import com.als.module.authentication.web.service.WebAuthenticationService
import com.als.module.user.service.UserService

class WebAuthenticationServiceImpl(sessionService: SessionService, userService: UserService) extends WebAuthenticationService
{
  def canLogin(credentials: LoginCredentials): Boolean =
  {
    userService.hasCredentials(credentials)
  }

  def login(credentials: LoginCredentials): Session =
  {
    val user = userService.getByCredentials(credentials)

    val authenticatedUser = AuthenticatedUser(
          id = user.id,
          username = user.username,
          firstName = user.firstName,
          lastName = user.lastName
      )

    sessionService.create(authenticatedUser)
  }

  def logout(sessionId: String)
  {
    sessionService.deleteBySessionId(sessionId)
  }
}
