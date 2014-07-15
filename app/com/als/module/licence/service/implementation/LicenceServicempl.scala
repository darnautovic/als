package com.als.module.licence.service.implementation

import com.als.domain.Licence.Full
import com.als.module.licence.dao.LicenceDao
import com.als.module.licence.service.LicenceService

class LicenceServiceImpl(licenceDao :LicenceDao) extends LicenceService{
   def getAllByApplicationId(id: Long): Seq[Full] = {
     licenceDao.findByApplicationId(id)
   }
}
