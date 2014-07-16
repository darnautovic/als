package com.als.module.serial.service

import com.als.domain.Serial
import com.als.domain.Serial.Full

trait SerialService {
  def getById(id : Long): Option[Serial.Full]
  def getAllByApplicationId(id : Long): Seq[Serial.Full]
  def generateSerials(applicationId:Long, numberOfLicenses: Long)
  def isCreated(serial :String) :Boolean
  def getIdBySerial(serial: String): Option[Full]
  def generateSerial(applicationId:Long)
}
