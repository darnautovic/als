package com.als.module.serial.dao.repository

import com.als.domain.Serial.{Create, Full}
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
          "serial_number" -> item.serialNumber,
          "created_on" -> DateUtils.jodaDateTimeToJavaDate(item.createdOn),
          "application_id" -> item.applicationId
        ).executeInsert()

        generatedId.get
    }
  }

  def findById(id: Long): Option[Serial.Full] = {
    DB.withConnection(dataSource.getName) { implicit connection =>
      FIND_BY_ID_QUERY.on("wantedValue" -> id).as[Option[Serial.Full]](SerialRowMapper.full.singleOpt)
    }
  }

  override def findByApplicationId(id: Long): Seq[Full] = {
    DB.withConnection(dataSource.getName) { implicit connection =>
      FIND_BY_APPLICATION_ID_QUERY.on("wantedValue" -> id).as[Seq[Serial.Full]](SerialRowMapper.full *)
    }
  }

  override def insertAll(items: Seq[Create]) = {

    DB.withTransaction(dataSource.getName) { implicit connection =>

      val serialsBatchSql = SQL(
        """
     INSERT INTO serials (application_id,  serial_number,  created_on)
     VALUES ({applicationId}, {serialNumber}, {createdOn})
        """).asBatch

      items.foldLeft(serialsBatchSql) {
        (serialsBatchSql, serial) =>
          serialsBatchSql.addBatch(
            "applicationId" -> serial.applicationId,
            "serialNumber" -> serial.serialNumber,
            "createdOn" -> DateUtils.jodaDateTimeToJavaDate(serial.createdOn)
          )
      }.execute()
    }
  }

  def findBySerial(serial: String): Option[Serial.Full] = {
    DB.withConnection(dataSource.getName) { implicit connection =>
      FIND_BY_SERIAL_QUERY.on("wantedValue" -> serial).as[Option[Serial.Full]](SerialRowMapper.full.singleOpt)
    }
  }

  def findAllByUserId(id: Long): Seq[Full] = {
    DB.withConnection(dataSource.getName) { implicit connection =>
      FIND_BY_USER_QUERY.on("wantedValue" -> id).as[Seq[Serial.Full]](SerialRowMapper.full*)
    }
  }
}

object SerialDaoImpl {
  val INSERT_SERIAL_QUERY: SqlQuery = SQL(
    """
     INSERT INTO serials (application_id,  serial_number,  created_on)
     VALUES ({application_id}, {serial_number}, {created_on})
    """)

   val SELECT_FROM_SERIALS =
    """
      | SELECT
      |   *
      | FROM serials
      | JOIN applications AS app ON app.id = serials.application_id
      | """.
      stripMargin

  val FIND_ALL_QUERY: SqlQuery = SQL(SELECT_FROM_SERIALS + "ORDER BY first_name ASC, last_name ASC, middle_name ASC")

  val FIND_BY_ID_QUERY: SqlQuery = SQL(SELECT_FROM_SERIALS + " WHERE serials.id={wantedValue}")
  val FIND_BY_SERIAL_QUERY: SqlQuery = SQL(SELECT_FROM_SERIALS + " WHERE serials.serial_number={wantedValue}")
  val FIND_BY_USER_QUERY: SqlQuery = SQL(SELECT_FROM_SERIALS + " WHERE app.user_id={wantedValue}")
  val FIND_BY_APPLICATION_ID_QUERY: SqlQuery = SQL(SELECT_FROM_SERIALS + " WHERE app.id={wantedValue}")
}
