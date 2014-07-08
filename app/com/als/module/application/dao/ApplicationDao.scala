package com.als.module.application.dao

import com.als.domain.Application

trait ApplicationDao {
  def insert(item: Application.Create): Long
  def update(item: Application.Edit)
  def findById(id: Long): Option[Application.Full]
}
