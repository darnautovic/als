package com.als.module.registry

import com.als.module.authentication.session.dao.SessionDao
import com.als.module.authentication.session.dao.repository.SessionDaoImpl
import com.als.module.authentication.session.service.SessionService
import com.als.module.authentication.session.service.implementation.SessionServiceImpl
import com.als.module.authentication.web.service.WebAuthenticationService
import com.als.module.authentication.web.service.implementation.WebAuthenticationServiceImpl
import com.als.module.client.dao.ClientDao
import com.als.module.client.dao.repository.ClientDaoImpl
import com.als.module.licence.dao.LicenceDao
import com.als.module.licence.dao.repository.LicenceDaoImpl
import com.als.module.serial.dao.SerialDao
import com.als.module.serial.dao.repository.SerialDaoImpl
import com.als.module.user.dao.UserDao
import com.als.module.user.dao.repository.UserDaoImpl
import com.als.module.user.service.UserService
import com.als.module.user.service.implementation.UserServiceImpl
import com.als.shared.DataSource

object ServiceRegistry {
  private val sessionDao : SessionDao = new SessionDaoImpl(DataSource.DEFAULT)
  private val userDao    : UserDao    = new UserDaoImpl(DataSource.DEFAULT)
  private val clientDao  : ClientDao  = new ClientDaoImpl(DataSource.DEFAULT)
  private val licenceDao : LicenceDao = new LicenceDaoImpl(DataSource.DEFAULT)
  private val serialDao  : SerialDao  = new SerialDaoImpl(DataSource.DEFAULT)

  val userService              :UserService              = new UserServiceImpl(userDao)
  val sessionService           :SessionService           = new SessionServiceImpl(sessionDao)
  val webAuthenticationService :WebAuthenticationService = new WebAuthenticationServiceImpl(sessionService, userService)


}
