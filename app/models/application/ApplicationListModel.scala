package models.application

import com.als.domain.Application
import play.api.data._

case class ApplicationListModel
(

  applications : Seq[Application.Full]
)

