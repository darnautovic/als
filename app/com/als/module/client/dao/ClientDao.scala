package com.als.module.client.dao

import com.als.domain.Client

trait ClientDao {
  def insert(item: Client.Create): Long
  def update(item: Client.Create)
  def findById(id: Long): Option[Client.Full]
}
