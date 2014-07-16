package controllers.api.web.licence

import com.als.module.registry.ServiceRegistry
import controllers.api.web.shared.AlsController
import models.licence.LicenceListModel


object LicenceController extends AlsController{

  private val licenseService = ServiceRegistry.licenceService


  def listLicences = AuthenticatedAction
  {
    implicit requestWithSession =>

      val licenceList     = licenseService.getAllByUserId(requestWithSession.session.user.id)

      Ok(views.html.licence.list(LicenceListModel(licenceList)))
  }
}
