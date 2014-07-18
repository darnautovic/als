package com.als.module.client.dao.repository

import play.api.Play.current
import com.als.domain.Client
import com.als.module.client.dao.ClientDao
import play.api.db.DB
import com.als.shared.DataSource
import anorm._


class ClientDaoImpl(val dataSource: DataSource) extends ClientDao {

  import ClientDaoImpl._

  def insert(item: Client.Create): Long = {
    DB.withTransaction(dataSource.getName) {
      implicit connection =>

        val generatedId: Option[Long] = INSERT_USER_QUERY.on(
          "first_name"     -> item.name,
          "last_name" -> item.lastName,
          "company"->   item.company.getOrElse("")
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

object ClientDaoImpl {
  val INSERT_USER_QUERY: SqlQuery = SQL(
    """
      | INSERT INTO clients
      |   ( first_name, company, last_name)
      | VALUES
      |   ({first_name}, {company}, {last_name})
    """.
      stripMargin)

  val UPDATE_CLIENT_QUERY: SqlQuery = SQL(
    """
      | UPDATE users
      | SET
    """.
      stripMargin)

  val SELECT_FROM_USERS =
    """
      | """.
      stripMargin

  val FIND_BY_ID_QUERY: SqlQuery = SQL(SELECT_FROM_USERS + " WHERE users.id={wantedValue}")
}
