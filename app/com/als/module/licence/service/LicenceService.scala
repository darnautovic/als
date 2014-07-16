package com.als.module.licence.service

import com.als.domain.{Client, Licence}
import com.google.common.base.Optional

trait LicenceService {
  def getById(id : Long): Option[Licence.Full]
  def getAllByApplicationId(id : Long): Seq[Licence.Full]
  def getAllByUserId(id : Long): Seq[Licence.Full]
  def getBySerialNumber(serial : String): Optional[Licence.Full]
  def createLicence(client :Client.Full, serialId :Long):Long
}
