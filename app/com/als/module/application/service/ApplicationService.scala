package com.als.module.application.service

import com.als.domain.Application


trait ApplicationService {
  def create(item: Application.Create)(implicit userInformation :Long): Long
  def getById(id :Long) : Option[Application.Full]
  def getAllByUserId(implicit userInformation :Long) : Seq[Application.Full]
}
