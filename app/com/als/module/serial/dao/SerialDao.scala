package com.als.module.serial.dao

import com.als.domain.Serial

trait SerialDao {
  def insert(item: Serial.Create): Long
  def insertAll(items: Seq[Serial.Create])
  def findById(id: Long): Option[Serial.Full]
  def findBySerial(serial: String): Option[Serial.Full]
  def findAllByUserId(id: Long): Seq[Serial.Full]
  def findByApplicationId(id: Long): Seq[Serial.Full]
}
