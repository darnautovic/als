package com.als.module.licence.service.implementation

import com.als.domain.{Serial, Client}
import com.als.domain.Licence.Full
import com.als.module.licence.dao.LicenceDao
import com.als.module.licence.service.LicenceService
import com.als.module.serial.service.SerialService
import com.google.common.base.Optional

class LicenceServiceImpl(serialService :SerialService, licenceDao :LicenceDao) extends LicenceService{
   def getAllByApplicationId(id: Long): Seq[Full] = {
     licenceDao.findByApplicationId(id)
   }

  def getBySerialNumber(serial: String): Optional[Full] = ???

  def createLicence(client: Client.Full, serialId: Long): Long = {
    val serial :Serial.Full = serialService.getById(serialId)

    val licence = client.name + client.lastName + client.company + serial

  }
}
