package module.authentication.web.service

import domain.authentication.{LoginCredentials, Session}

trait WebAuthenticationService
{
    def canLogin(credentials: LoginCredentials): Boolean
    def login(credentials: LoginCredentials): Session
    def logout(sessionId: String)
}
