package module.user.dao

import domain.User
import domain.authentication.LoginCredentials

trait UserDao
{
  def insert(item: User.Create): Long
  def update(item: User.Edit)
  def findAll: Seq[User.Full]
  def findById(id: Long): Option[User.Full]
  def findByUsername(username: String): Option[User.Full]
  def findByCredentials(credentials: LoginCredentials): Option[User.Full]
  def changePassword(credentials: LoginCredentials)
}




