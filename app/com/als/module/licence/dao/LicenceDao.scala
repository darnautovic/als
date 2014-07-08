package com.als.module.licence.dao

import com.als.domain.Licence

trait LicenceDao {
  def insert(item: Licence.Create): Long
  def update(item: Licence.Edit)
  def findById(id: Long): Option[Licence.Full]
}