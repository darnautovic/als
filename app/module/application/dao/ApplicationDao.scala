package module.application.dao

import domain.Application

trait ApplicationDao {
  def insert(item: Application.Create): Long
  def update(item: Application.Edit)
  def findById(id: Long): Option[Application.Full]
}
