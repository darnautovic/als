package com.als.module.application.dao.repository

import com.als.domain.Application.Full
import play.api.Play.current
import com.als.domain.Application
import com.als.module.application.dao.ApplicationDao
import com.als.shared.DataSource
import anorm._
import play.api.db.DB

class   ApplicationDaoImpl(val dataSource: DataSource) extends ApplicationDao {


  import ApplicationDaoImpl._

  def insert(userId: Long, item: Application.Create): Long = {
    DB.withTransaction(dataSource.getName) {
      implicit connection =>

        val keysId: Option[Long] = insertKeys(item).executeInsert()

        val generatedId: Option[Long] = INSERT_APPLICATION_QUERY.on(
          "userId" -> userId,
          "keyId" -> keysId.get,
          "name" -> item.name,
          "version" -> item.version
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

  def findAllByUserId(userId: Long): Seq[Application.Full] = {
    DB.withConnection(dataSource.getName) { implicit connection =>
      FIND_BY_USER_ID_QUERY.on("wantedValue" -> userId).as[Seq[Application.Full]](ApplicationRowMapper.full *)
    }
  }

  def findBySerial(id: String): Option[Full] = {
    DB.withConnection(dataSource.getName) { implicit connection =>
      FIND_BY_SERIAL_ID_QUERY.on("wantedValue" -> id).as[Option[Application.Full]](ApplicationRowMapper.full.singleOpt)
    }
  }
}

object ApplicationDaoImpl {
  val INSERT_APPLICATION_QUERY: SqlQuery = SQL(
    """
      | INSERT INTO applications
      |   (user_id,  key_id,   name,   version)
      | VALUES
      |   ({userId}, {keyId}, {name}, {version})
    """.
      stripMargin)

  val UPDATE_APPLICATION_QUERY: SqlQuery = SQL(
    """
      | UPDATE users
      | SET
      | WHERE
    """.
      stripMargin)

  val SELECT_FROM_APPLICATIONS3 =
    """
      | SELECT * FROM applications
      | JOIN users as usr  ON usr.id = applications.user_id
      | JOIN keys  as key  ON key.id = applications.key_id
      | JOIN serials as ser  ON ser.application_id = applications.id
      | """.
      stripMargin

  val SELECT_FROM_APPLICATIONS =
    """
      | SELECT * FROM applications
      | JOIN users as usr  ON usr.id = applications.user_id
      | JOIN keys  as key  ON key.id = applications.key_id
      | """.
      stripMargin

  val FIND_ALL_QUERY: SqlQuery = SQL(SELECT_FROM_APPLICATIONS + "ORDER BY first_name ASC, last_name ASC, middle_name ASC")

  val FIND_BY_ID_QUERY: SqlQuery = SQL(SELECT_FROM_APPLICATIONS + " WHERE applications.id={wantedValue}")
  val FIND_BY_SERIAL_ID_QUERY: SqlQuery = SQL(SELECT_FROM_APPLICATIONS3 + " WHERE ser.serial_number={wantedValue}")
  val FIND_BY_USER_ID_QUERY: SqlQuery = SQL(SELECT_FROM_APPLICATIONS + " WHERE usr.id = {wantedValue}")

  def insertKeys(item :Application.Create) =
  {
    SQL("INSERT INTO keys (public_key, private_key) VALUES ({publicKey}, {privateKey})")
      .on("publicKey"   -> item.publicKey.get, "privateKey" -> item.privateKey.get)
  }
}

