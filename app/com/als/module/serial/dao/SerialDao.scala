package com.als.module.serial.dao

import com.als.domain.Serial

trait SerialDao {
  def insert(item: Serial.Create): Long
  def findById(id: Long): Option[Serial.Full]
}
