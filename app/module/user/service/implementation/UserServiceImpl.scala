package module.user.service.implementation

import domain.User
import module.user.dao.UserDao
import domain.authentication.LoginCredentials
import module.user.service.UserService
import shared.utils.crypto.HashUtils

class UserServiceImpl(userDao :UserDao) extends UserService  {

  def create(item: User.Create): Long =
  {
    val generatedId = userDao.insert(item)

    generatedId
  }

  def update(item: User.Edit)
  {
    if(item.password.isDefined)
    {
      val itemWithHashedPassword = item.copy(password = Option(HashUtils.sha1(item.password.get)))
      userDao.update(itemWithHashedPassword)
    }
    else
    {
      val fullItem = userDao.findById(item.id)
      val itemWithUnchangedPassword = item.copy(password = Option(fullItem.get.password))

      userDao.update(itemWithUnchangedPassword)
    }
  }

  def getById(id : Long) : User.Full =
  {
    userDao.findById(id).getOrElse(throw new RuntimeException("User not found"))
  }

  def getAll : Seq[User.Full] =
  {
    userDao.findAll
  }

  def hasCredentials(credentials: LoginCredentials): Boolean =
  {
    val credentialsWithHashedPassword = credentials.copy(password = HashUtils.sha1(credentials.password))

    userDao.findByCredentials(credentialsWithHashedPassword).isDefined
  }

  def getByCredentials(credentials: LoginCredentials) : User.Full =
  {
    val credentialsWithHashedPassword = credentials.copy(password = HashUtils.sha1(credentials.password))

    userDao.findByCredentials(credentialsWithHashedPassword)
      .getOrElse(throw new IllegalStateException("User " + credentials.username + " with given credentials is not found"))
  }

  def isUsernameRegistered(userName: String) : Boolean =
  {
    userDao.findByUsername(userName).isDefined
  }

  def changePassword(currentCredentials: LoginCredentials, newCredentials: LoginCredentials)
  {
    if(!hasCredentials(currentCredentials))
    {
      throw new IllegalStateException("Current password for user " + currentCredentials.username + " is incorrect")
    }

    val newCredentialsWithHashedPassword = newCredentials.copy(password = HashUtils.sha1(newCredentials.password))

    userDao.changePassword(newCredentialsWithHashedPassword)
  }
}
