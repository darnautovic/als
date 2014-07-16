package com.als.module.serial.service.implementation

import java.util.UUID

import com.als.domain.{Serial, Licence}
import com.als.domain.Serial.Full
import com.als.module.serial.dao.SerialDao
import com.als.module.serial.service.SerialService
import com.als.shared.utils.date.DateUtils

class SerialServiceImpl(serialDao :SerialDao) extends SerialService {

  def getById(id: Long): Option[Full] = {
    serialDao.findById(id)
  }

  def getIdBySerial(serial: String): Option[Full] = {
    serialDao.findBySerial(serial)
  }


  def isCreated(serial :String) :Boolean = {
    serialDao.findBySerial(serial).isDefined
  }

  def getAllByApplicationId(id: Long): Seq[Full]  = {
     serialDao.findByApplicationId(id)
   }

  def generateSerials(applicationId:Long, numberOfLicenses: Long)  = {

    val generatedSerials: Seq[Serial.Create] = 1L to numberOfLicenses map {_ => Serial.Create(applicationId, UUID.randomUUID().toString, DateUtils.nowDateTimeUTC())}

    serialDao.insertAll(generatedSerials)
  }

  def generateSerial(applicationId: Long): Unit = {

     val generatedSerial: Serial.Create = Serial.Create(applicationId, UUID.randomUUID().toString, DateUtils.nowDateTimeUTC())

     serialDao.insert(generatedSerial)
   }

   def getAllByUserId(id: Long): Seq[Full] =
   {
     serialDao.findAllByUserId(id :Long)
   }
}
