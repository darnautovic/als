package com.als.module.serial.service

import com.als.domain.Serial

trait SerialService {
  def getById(id : Long): Serial.Full
  def getAllByApplicationId(id : Long): Seq[Serial.Full]
  def generateSerials(applicationId:Long, numberOfLicenses: Long)
  def generateSerial(applicationId:Long)
}
