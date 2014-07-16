package controllers.api.web.serial

import com.als.module.registry.ServiceRegistry
import controllers.api.web.shared.AlsController
import models.application.ApplicationPageModel
import models.licence.LicenceListModel
import models.serial.SerialListModel


object SerialController extends AlsController{

  private val applicationService = ServiceRegistry.applicationService
  private val serialService = ServiceRegistry.serialService
  private val licenseService = ServiceRegistry.licenceService

  def generateSerials(id :Long) = AuthenticatedAction
  {
    implicit requestWithSession =>

      serialService.generateSerials(id, 10)

      val application     = applicationService.getById(id)
      val serialList      = serialService.getAllByApplicationId(id)
      val licenseList     = licenseService.getAllByApplicationId(id)

      Ok(views.html.application.applicationPage(ApplicationPageModel(application, serialList, licenseList)))
  }

  def generateSerial(id :Long) = AuthenticatedAction
  {
    implicit requestWithSession =>

      serialService.generateSerial(id)

      val application     = applicationService.getById(id)
      val serialList      = serialService.getAllByApplicationId(id)
      val licenseList     = licenseService.getAllByApplicationId(id)

      Ok(views.html.application.applicationPage(ApplicationPageModel(application, serialList, licenseList)))
  }

  def listSerials = AuthenticatedAction
  {
    implicit requestWithSession =>

      val serialList     = serialService.getAllByUserId(requestWithSession.session.user.id)

      Ok(views.html.serial.list(SerialListModel(serialList)))
  }
}
