package com.als.module.licence.service

import com.als.domain.Licence

trait LicenceService {
  def getAllByApplicationId(id : Long): Seq[Licence.Full]
}
