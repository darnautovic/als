package controllers.api.activation

import java.io.OutputStream
import java.util.zip.ZipEntry

import play.api.libs.iteratee.Enumerator
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import com.als.domain.Client
import com.als.module.registry.ServiceRegistry
import models.activation.ActivationData
import play.api.libs.json.{JsError, Json, Writes, JsPath}
import play.api.mvc.{Controller, BodyParsers, Action}


object ActivationController extends Controller{

  private val clientService = ServiceRegistry.clientService
  private val serialService = ServiceRegistry.serialService
  private val licenseService = ServiceRegistry.licenceService

  def registerClient = Action(BodyParsers.parse.json) { request =>

    implicit val placeWrites: Writes[ActivationData] = (
      (JsPath \ "name").write[String] and
        (JsPath \ "secondName").write[String] and
        (JsPath \ "company").write[String] and
        (JsPath \ "serial").write[String]
      )(unlift(ActivationData.unapply))


    implicit val locationReads: Reads[ActivationData] = (
      (JsPath \ "name").read[String] and
        (JsPath \ "secondName").read[String] and
        (JsPath \ "company").read[String] and
        (JsPath \ "serial").read[String]
      )(ActivationData.apply _)



    val activationResult = request.body.validate[ActivationData]

    activationResult.fold(
      errors => {
        BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(errors)))
      },
      place => {

        if(serialService.isCreated(place.serial))
        {
          val serialid = serialService.getIdBySerial(place.serial).get.id
          val clientId = clientService.create(Client.Create(place.name, place.secondName, Option(place.company)))
          val licence =  licenseService.createLicence(Client.Full(clientId, place.name, place.secondName, Option(place.company)), serialid)
          Ok(Json.obj("status" ->"OK", "message" -> ("Licence created with id:" + licence.toString) ))
        }
        else
        {
          BadRequest(Json.obj("status" ->"KO", "message" -> "serial does not exist"))
        }


      }
    )
  }
}

