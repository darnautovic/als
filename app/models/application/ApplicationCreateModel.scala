package models.application

import com.als.domain.Application
import play.api.data._
import play.api.data.Forms._

case class ApplicationCreateModel
(
  form: Form[Application.Create]
)

object ApplicationCreateForm
{
  val form: Form[Application.Create] = Form(
    mapping(
    "name"-> nonEmptyText,
    "version" -> nonEmptyText
    )
    {(name, version) => Application.Create(name, version, None, None) }
    { (application :Application.Create) => Some(application.name, application.version)}
  )
}
