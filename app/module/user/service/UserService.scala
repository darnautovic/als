package module.user.service

import domain.User
import domain.authentication.LoginCredentials

trait UserService {
  def create(item: User.Create): Long
  def update(item: User.Edit)

  def getById(id : Long): User.Full
  def getAll: Seq[User.Full]

  def hasCredentials(credentials: LoginCredentials): Boolean
  def getByCredentials(credentials: LoginCredentials): User.Full
  def isUsernameRegistered(username: String): Boolean

  def changePassword(currentCredentials: LoginCredentials, newCredentials: LoginCredentials)
}
