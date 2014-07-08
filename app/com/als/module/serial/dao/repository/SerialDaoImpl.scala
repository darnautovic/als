package com.als.module.serial.dao.repository

import play.api.Play.current
import anorm._
import com.als.domain.Serial
import com.als.module.serial.dao.SerialDao
import play.api.db.DB
import com.als.shared.DataSource
import com.als.shared.utils.date.DateUtils


class SerialDaoImpl(val dataSource: DataSource) extends SerialDao {

  import SerialDaoImpl._

  def insert(item: Serial.Create): Long = {
    DB.withTransaction(dataSource.getName) {
      implicit connection =>

        val generatedId: Option[Long] = INSERT_SERIAL_QUERY.on(
          "serial_id"     -> item.applicationId,
          "created_on"    -> item.serialNumber,
          "active"        -> DateUtils.jodaDateTimeToJavaDate(item.createdOn)
        ).executeInsert()

        generatedId.get
    }
  }

  def findById(id: Long): Option[Serial.Full] = {
    DB.withConnection(dataSource.getName) { implicit connection =>
      FIND_BY_ID_QUERY.on("wantedValue" -> id).as[Option[Serial.Full]](SerialRowMapper.full.singleOpt)
    }
  }
}

object SerialDaoImpl {
  val INSERT_SERIAL_QUERY: SqlQuery = SQL(
    """
      | INSERT INTO users
      |   (external_id,       username,   password,   first_name,  middle_name,  last_name,  email,   phone,   mobile,
      |   skype,   notes,   is_credit_officer, change_password_on_next_login, organisation_structure_node_id, top_visible_node_id)
      | VALUES
      |   ({externalId}, {username}, {password}, {firstName}, {middleName}, {lastName}, {email}, {phone}, {mobile},
      |   {skype}, {notes}, {isCreditOfficer}, {changePasswordOnNextLogin},    {organisationStructureNodeId}, {topVisibleNodeId})
    """.
      stripMargin)

   val SELECT_FROM_SERIALS =
    """
      | SELECT
      |   users.* ,
      |   org.parent_id AS parent_organization_node_id
      | FROM users
      | JOIN organisation_structure_nodes AS org ON org.id=users.organisation_structure_node_id
      | """.
      stripMargin

  val FIND_ALL_QUERY: SqlQuery = SQL(SELECT_FROM_SERIALS + "ORDER BY first_name ASC, last_name ASC, middle_name ASC")

  val FIND_BY_ID_QUERY: SqlQuery = SQL(SELECT_FROM_SERIALS + " WHERE users.id={wantedValue}")
}
