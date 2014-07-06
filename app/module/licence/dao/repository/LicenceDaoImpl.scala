package module.licence.dao.repository

import play.api.Play.current
import anorm._
import domain.Licence
import module.licence.dao.LicenceDao
import play.api.db.DB
import shared.DataSource
import shared.utils.DateUtils

class LicenceDaoImpl(val dataSource: DataSource) extends LicenceDao{

  import LicenceDaoImpl._

  def insert(item: Licence.Create): Long = {
    DB.withTransaction(dataSource.getName) {
      implicit connection =>

        val generatedId: Option[Long] = INSERT_LICENCE_QUERY.on(
          "serial_id"     -> item.serialNumberId,
          "created_on"    -> DateUtils.jodaDateTimeToJavaDate(item.createdOn),
          "active"        -> true,
          "licence_hash"  -> item.licenceHash,
          "public_key"    -> item.keys.publicKey,
          "private_key"   -> item.keys.privateKey
        ).executeInsert()

        generatedId.get
    }
  }

  def update(item: Licence.Edit) {
    DB.withTransaction(dataSource.getName) {

      implicit connection =>
        UPDATE_LICENCE_QUERY.on("active" -> item.active).executeUpdate()
    }
  }

  def findById(id: Long): Option[Licence.Full] = {
    DB.withConnection(dataSource.getName) { implicit connection =>
      FIND_BY_ID_QUERY.on("wantedValue" -> id).as[Option[Licence.Full]](LicenceRowMapper.full.singleOpt)
    }
  }
}

object LicenceDaoImpl {
  val INSERT_LICENCE_QUERY: SqlQuery = SQL(
    """
      | INSERT INTO users
      |   (external_id,       username,   password,   first_name,  middle_name,  last_name,  email,   phone,   mobile,
      |   skype,   notes,   is_credit_officer, change_password_on_next_login, organisation_structure_node_id, top_visible_node_id)
      | VALUES
      |   ({externalId}, {username}, {password}, {firstName}, {middleName}, {lastName}, {email}, {phone}, {mobile},
      |   {skype}, {notes}, {isCreditOfficer}, {changePasswordOnNextLogin},    {organisationStructureNodeId}, {topVisibleNodeId})
    """.
      stripMargin)

  val UPDATE_LICENCE_QUERY: SqlQuery = SQL(
    """
      | UPDATE users
      | SET
      |   password={password},   first_name={firstName},  middle_name= {middleName},
      |   last_name={lastName},  email={email},   phone={phone},   mobile={mobile},   skype={skype},   notes={notes},
      |   is_credit_officer={isCreditOfficer}, change_password_on_next_login={changePasswordOnNextLogin}, top_visible_node_id={topVisibleNodeId}
      | WHERE
      |   id={id}
    """.
      stripMargin)

  val SELECT_FROM_LICENCES =
    """
      | SELECT
      |   users.* ,
      |   org.parent_id AS parent_organization_node_id
      | FROM users
      | JOIN organisation_structure_nodes AS org ON org.id=users.organisation_structure_node_id
      | """.
      stripMargin

  val FIND_ALL_QUERY: SqlQuery = SQL(SELECT_FROM_LICENCES + "ORDER BY first_name ASC, last_name ASC, middle_name ASC")

  val FIND_BY_ID_QUERY: SqlQuery = SQL(SELECT_FROM_LICENCES + " WHERE users.id={wantedValue}")
}
