package module.serial.dao

import domain.Serial

trait SerialDao {
  def insert(item: Serial.Create): Long
  def findById(id: Long): Option[Serial.Full]
}
