package models.licence

import com.als.domain.{Licence, Application}

case class LicenceListModel
(

  licences : Seq[Licence.Full]
)

