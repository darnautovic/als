package com.als.module.licence.service.implementation

import com.als.domain.{Licence, Serial, Client}
import com.als.domain.Licence.Full
import com.als.module.application.service.ApplicationService
import com.als.module.licence.dao.LicenceDao
import com.als.module.licence.service.LicenceService
import com.als.module.serial.service.SerialService
import com.als.shared.utils.crypto.{CryptoUtils, HashUtils}
import com.als.shared.utils.date.DateUtils
import com.google.common.base.Optional

class LicenceServiceImpl(serialService :SerialService, applicationService :ApplicationService, licenceDao :LicenceDao) extends LicenceService{
   def getAllByApplicationId(id: Long): Seq[Full] = {
     licenceDao.findByApplicationId(id)
   }

  def getBySerialNumber(serial: String): Optional[Full] = ???

  def createLicence(client: Client.Full, serialId: Long): Long = {
    val serial :Serial.Full = serialService.getById(serialId).get
    val application = applicationService.getBySerial(serial.serialNumber)

    val licencew :String = client.name + client.lastName + client.company + serial.serialNumber

    val licenceHash = HashUtils.sha1(licencew)

    val licenceSigned = CryptoUtils.rsa.sign(CryptoUtils.rsa.decodePrivateKey(CryptoUtils.rsa.base64KeyToEncodedKey(application.get.privateKey.get)), licenceHash.getBytes)

    val licence = Licence.Create(serialId, client.id, DateUtils.nowDateTimeUTC(), true, licenceHash, CryptoUtils.rsa.encodedKeyToBase64Key(licenceSigned) )

    licenceDao.insert(licence)
  }

   def getById(id: Long): Option[Full] = {
    licenceDao.findById(id)
  }
}
