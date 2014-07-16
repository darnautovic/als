package com.als.module.client.service.implementation

import com.als.domain.Client
import com.als.module.client.dao.ClientDao
import com.als.module.client.service.ClientService


class ClientServiceImpl(clientDao :ClientDao) extends ClientService {
  def create(item: Client.Create): Long =
  {
    val generatedId = clientDao.insert(item)

    generatedId
  }

}
