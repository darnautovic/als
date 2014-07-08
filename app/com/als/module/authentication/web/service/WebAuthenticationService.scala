package com.als.module.authentication.web.service

import com.als.domain.authentication.{LoginCredentials, Session}

trait WebAuthenticationService
{
    def canLogin(credentials: LoginCredentials): Boolean
    def login(credentials: LoginCredentials): Session
    def logout(sessionId: String)
}
