package com.als.module.licence.service

import com.als.domain.{Client, Licence}
import com.google.common.base.Optional

trait LicenceService {
  def getAllByApplicationId(id : Long): Seq[Licence.Full]
  def getBySerialNumber(serial : String): Optional[Licence.Full]
  def createLicence(client :Client.Full, serialId :Long)
}
