package com.als.module.serial.service.implementation

import java.util.UUID

import com.als.domain.{Serial, Licence}
import com.als.domain.Serial.Full
import com.als.module.serial.dao.SerialDao
import com.als.module.serial.service.SerialService
import com.als.shared.utils.date.DateUtils

class SerialServiceImpl(serialDao :SerialDao) extends SerialService {
   def getAllByApplicationId(id: Long): Seq[Full]  = {
     serialDao.findByApplicationId(id)
  }

  def generateSerials(applicationId:Long, numberOfLicenses: Long)  = {

    val generatedSerials: Seq[Serial.Create] = 1L to numberOfLicenses map {_ => Serial.Create(applicationId, UUID.randomUUID().toString, DateUtils.nowDateTimeUTC())}

    serialDao.insertAll(generatedSerials)
  }
}
