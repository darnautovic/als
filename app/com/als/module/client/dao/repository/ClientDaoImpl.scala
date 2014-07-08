package com.als.module.client.dao.repository

import play.api.Play.current
import com.als.domain.Client
import com.als.module.client.dao.ClientDao
import play.api.db.DB
import com.als.shared.DataSource
import anorm._


class ClientDaoImpl(val dataSource: DataSource) extends ClientDao {

  import UserDaoImpl._

  def insert(item: Client.Create): Long = {
    DB.withTransaction(dataSource.getName) {
      implicit connection =>

        val generatedId: Option[Long] = INSERT_USER_QUERY.on(
          "name"     -> item.name,
          "lastName" -> item.lastName,
          "company"->   item.company,
          "lastName" -> item.lastName
        ).executeInsert()

        generatedId.get
    }
  }

  def update(item: Client.Create) =
  {
    DB.withTransaction(dataSource.getName) {
      implicit connection =>

        val generatedId: Option[Long] = UPDATE_CLIENT_QUERY.on(
          "name"     -> item.name,
          "lastName" -> item.lastName,
          "company"->   item.company,
          "lastName" -> item.lastName
        ).executeInsert()

        generatedId.get
    }
  }

  def findById(id: Long): Option[Client.Full] =
  {
    DB.withConnection(dataSource.getName) { implicit connection =>
      FIND_BY_ID_QUERY.on("wantedValue" -> id).as[Option[Client.Full]](ClientRowMapper.full.singleOpt)
    }
  }
}

object UserDaoImpl {
  val INSERT_USER_QUERY: SqlQuery = SQL(
    """
      | INSERT INTO users
      |   (external_id,       username,   password,   first_name,  middle_name,  last_name,  email,   phone,   mobile,
      |   skype,   notes,   is_credit_officer, change_password_on_next_login, organisation_structure_node_id, top_visible_node_id)
      | VALUES
      |   ({externalId}, {username}, {password}, {firstName}, {middleName}, {lastName}, {email}, {phone}, {mobile},
      |   {skype}, {notes}, {isCreditOfficer}, {changePasswordOnNextLogin},    {organisationStructureNodeId}, {topVisibleNodeId})
    """.
      stripMargin)

  val UPDATE_CLIENT_QUERY: SqlQuery = SQL(
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

  val SELECT_FROM_USERS =
    """
      | SELECT
      |   users.* ,
      |   org.parent_id AS parent_organization_node_id
      | FROM users
      | JOIN organisation_structure_nodes AS org ON org.id=users.organisation_structure_node_id
      | """.
      stripMargin

  val FIND_BY_ID_QUERY: SqlQuery = SQL(SELECT_FROM_USERS + " WHERE users.id={wantedValue}")
}
