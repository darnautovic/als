package com.als.module.client.service

import com.als.domain.Client


trait ClientService {
  def create(item: Client.Create): Long
}
