package com.als.module.serial.service.implementation

import com.als.domain.Serial.Full
import com.als.module.serial.dao.SerialDao
import com.als.module.serial.service.SerialService

class SerialServiceImpl(serialDao :SerialDao) extends SerialService {
   def getAllByApplicationId(id: Long): Seq[Full]  = {
     serialDao.findByApplicationId(id)
  }
}
