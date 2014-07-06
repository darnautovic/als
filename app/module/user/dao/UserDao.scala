package module.user.dao

import domain.User

trait UserDao
{
  def insert(item: User.Create): Long
  def update(item: User.Edit)
  def findAll: Seq[User.Full]
  def findById(id: Long): Option[User.Full]
  def findByUsername(username: String): Option[User.Full]
}




