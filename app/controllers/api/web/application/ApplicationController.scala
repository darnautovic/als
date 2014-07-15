package controllers.api.web.application

import com.als.module.registry.ServiceRegistry
import controllers.api.web.shared.AlsController
import models.application.{ApplicationPageModel, ApplicationListModel, ApplicationCreateForm, ApplicationCreateModel}


object ApplicationController extends AlsController{

  private val applicationService = ServiceRegistry.applicationService
  private val serialService = ServiceRegistry.serialService
  private val licenseService = ServiceRegistry.licenceService

  def applicationPage(id :Long) = AuthenticatedAction
  {
    implicit requestWithSession =>

      val application     = applicationService.getById(id)
      val serialList      = serialService.getAllByApplicationId(id)
      val licenseList     = licenseService.getAllByApplicationId(id)

      Ok(views.html.application.applicationPage(ApplicationPageModel(application, serialList, licenseList)))
  }

  def listPage = AuthenticatedAction
  {
    implicit requestWithSession =>

      val applicationList = applicationService.getAllByUserId(requestWithSession.session.user.id )

      Ok(views.html.application.list(ApplicationListModel(applicationList)))
  }

  def createPage = AuthenticatedAction
  {
    implicit requestWithSession =>

      val formWithDefaultValues =  ApplicationCreateForm.form

      Ok(views.html.application.create(ApplicationCreateModel(formWithDefaultValues)))
  }

  def create = AuthenticatedAction
  {
    implicit requestWithSession =>
      val form = ApplicationCreateForm.form.bindFromRequest()(requestWithSession.request)

      form.fold(
        formWithErrors =>
        {
          BadRequest(views.html.application.create(ApplicationCreateModel(form)))
        },
        application =>
        {
          implicit val userInformation :Long = requestWithSession.session.user.id
          val generatedId = applicationService.create(application)

          Redirect(routes.ApplicationController.listPage())
       })
  }
}
