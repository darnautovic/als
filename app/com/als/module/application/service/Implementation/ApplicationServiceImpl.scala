package com.als.module.application.service.Implementation

import com.als.domain.Application
import com.als.domain.Application.Full
import com.als.module.application.dao.ApplicationDao
import com.als.module.application.service.ApplicationService
import com.als.shared.utils.crypto.CryptoUtils


class ApplicationServiceImpl (applicationDao : ApplicationDao) extends ApplicationService {

   def create(item: Application.Create)(implicit userInformation: Long): Long =
   {

     val keyPair = CryptoUtils.rsa.generateKeyPair

     val base64PublicKey  = CryptoUtils.rsa.encodedKeyToBase64Key(keyPair._1.getEncoded)
     val base64PrivateKey       = CryptoUtils.rsa.encodedKeyToBase64Key(keyPair._2.getEncoded)

     val applicationWithKeys = item.copy(publicKey = Option(base64PublicKey), privateKey = Option(base64PrivateKey))

     applicationDao.insert(userInformation, applicationWithKeys)
   }

   def getById(id: Long): Option[Full] = {
     applicationDao.findById(id)
   }

   def getAllByUserId(implicit userInformation: Long): Seq[Full] = {
     applicationDao.findAllByUserId(userInformation)
   }
}
