package com.als.module.authentication.session.dao.repository

import com.als.domain.authentication.{AuthenticatedUser, AuthenticatedUserJsonMapper, Session}
import com.als.module.authentication.session.dao.SessionDao
import anorm._
import com.als.module.authentication.session.service.implementation.SessionRowMapper
import org.joda.time.DateTime
import play.api.db.DB
import com.als.shared.DataSource
import com.als.shared.utils.date.DateUtils
import play.api.Play.current

class SessionDaoImpl(val dataSource: DataSource) extends SessionDao
{
  def insert(item: Session) =
  {
    DB.withConnection(dataSource.getName) { implicit connection =>
      SQL(
        """
        INSERT INTO
          sessions ( session_id, created_on,  expires_on,   data)
          VALUES   ({sessionId}, {createdOn}, {expiresOn}, {data})
        """)
        .on(
          "sessionId" -> item.sessionId,
          "createdOn" -> DateUtils.jodaDateTimeToJavaDate(item.createdOn),
          "expiresOn" -> DateUtils.jodaDateTimeToJavaDate(item.expiresOn),
          "data"      -> AuthenticatedUserJsonMapper.toJson(item.user)
        )
        .executeInsert()
    }
  }

  def findBySessionId(sessionId: String): Option[Session] =
  {
    DB.withConnection(dataSource.getName) { implicit connection =>
      SQL("SELECT * FROM sessions WHERE session_id={sessionId}")
        .on("sessionId" -> sessionId)
        .singleOpt(SessionRowMapper.mapping)
    }
  }

  def deleteBySessionId(sessionId: String)
  {
    DB.withConnection(dataSource.getName) { implicit connection =>
      SQL("DELETE FROM sessions WHERE session_id={sessionId}")
        .on("sessionId" -> sessionId)
        .execute()
    }
  }

  def deleteExpiredSessions()
  {
    DB.withConnection(dataSource.getName) { implicit connection =>
      SQL("DELETE FROM sessions WHERE expires_on<{currentDateTime}")
        .on("currentDateTime" -> DateUtils.jodaDateTimeToJavaDate(DateUtils.nowDateTimeUTC()))
        .execute()
    }
  }

  def setDataForSessionId(sessionId: String, user: AuthenticatedUser)
  {
    DB.withConnection(dataSource.getName) { implicit connection =>
      SQL("UPDATE sessions SET data={data} WHERE session_id={sessionId}")
        .on(
          "data"      -> AuthenticatedUserJsonMapper.toJson(user),
          "sessionId" -> sessionId
        )
        .executeUpdate()
    }
  }

  def updateExpirationDate(sessionId: String, expirationDate: DateTime) =
  {
    DB.withConnection(dataSource.getName) { implicit connection =>
      SQL("UPDATE sessions SET expires_on={expirationDate} WHERE session_id={sessionId}")
        .on(
          "expirationDate"  -> DateUtils.jodaDateTimeToJavaDate(expirationDate),
          "sessionId"       -> sessionId
        )
        .executeUpdate()
    }
  }
}

