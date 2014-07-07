package module.application.dao.repository

import play.api.Play.current
import domain.Application
import module.application.dao.ApplicationDao
import shared.DataSource
import anorm._
import play.api.db.DB

class ApplicationDaoImpl(val dataSource: DataSource) extends ApplicationDao {


  import ApplicationDaoImpl._

  def insert(item: Application.Create): Long = {
    DB.withTransaction(dataSource.getName) {
      implicit connection =>

        val generatedId: Option[Long] = INSERT_APPLICATION_QUERY.on(
          "user_id"     -> item.userId,
          "name"        -> item.name,
          "version"     -> item.version
        ).executeInsert()

        generatedId.get
    }
  }

  def update(item: Application.Edit) {
    DB.withTransaction(dataSource.getName) {

      implicit connection =>
        UPDATE_APPLICATION_QUERY.on("name" -> item.name).executeUpdate()
    }
  }

  def findById(id: Long): Option[Application.Full] = {
    DB.withConnection(dataSource.getName) { implicit connection =>
      FIND_BY_ID_QUERY.on("wantedValue" -> id).as[Option[Application.Full]](ApplicationRowMapper.full.singleOpt)
    }
  }
}

object ApplicationDaoImpl {
  val INSERT_APPLICATION_QUERY: SqlQuery = SQL(
    """
      | INSERT INTO users
      |   (external_id,       username,   password,   first_name,  middle_name,  last_name,  email,   phone,   mobile,
      |   skype,   notes,   is_credit_officer, change_password_on_next_login, organisation_structure_node_id, top_visible_node_id)
      | VALUES
      |   ({externalId}, {username}, {password}, {firstName}, {middleName}, {lastName}, {email}, {phone}, {mobile},
      |   {skype}, {notes}, {isCreditOfficer}, {changePasswordOnNextLogin},    {organisationStructureNodeId}, {topVisibleNodeId})
    """.
      stripMargin)

  val UPDATE_APPLICATION_QUERY: SqlQuery = SQL(
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

  val SELECT_FROM_APPLICATIONS =
    """
      | SELECT
      |   users.* ,
      |   org.parent_id AS parent_organization_node_id
      | FROM users
      | JOIN organisation_structure_nodes AS org ON org.id=users.organisation_structure_node_id
      | """.
      stripMargin

  val FIND_ALL_QUERY: SqlQuery = SQL(SELECT_FROM_APPLICATIONS + "ORDER BY first_name ASC, last_name ASC, middle_name ASC")

  val FIND_BY_ID_QUERY: SqlQuery = SQL(SELECT_FROM_APPLICATIONS + " WHERE users.id={wantedValue}")
}
