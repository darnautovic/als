package com.als.module.user.dao.repository

import com.als.domain.authentication.LoginCredentials
import play.api.Play.current
import anorm._
import com.als.domain.User
import com.als.module.user.dao.UserDao
import play.api.db.DB
import com.als.shared.DataSource

class UserDaoImpl(val dataSource: DataSource) extends UserDao {

  import UserDaoImpl._

  def insert(item: User.Create): Long = {
    DB.withTransaction(dataSource.getName) {
      implicit connection =>

        val generatedId: Option[Long] = INSERT_USER_QUERY.on(
          "username" -> item.username,
          "password" -> item.password,
          "firstName" -> item.firstName,
          "lastName" -> item.lastName,
          "email" -> item.email
        ).executeInsert()

        generatedId.get
    }
  }

  def update(item: User.Edit) {
    DB.withTransaction(dataSource.getName) {

      implicit connection =>

        UPDATE_USER_QUERY.on(
          "id" -> item.id,
          "password" -> item.password,
          "firstName" -> item.firstName,
          "lastName" -> item.lastName,
          "email" -> item.email
        ).executeUpdate()
    }
  }

    def findAll: Seq[User.Full] = {
      DB.withConnection(dataSource.getName) { implicit connection =>
        FIND_ALL_QUERY.as[List[User.Full]](UserRowMapper.full.*)
      }
    }

    def findById(id: Long): Option[User.Full] = {
      DB.withConnection(dataSource.getName) { implicit connection =>
        FIND_BY_ID_QUERY.on("wantedValue" -> id).as[Option[User.Full]](UserRowMapper.full.singleOpt)
      }
    }

    def findByUsername(username: String): Option[User.Full] = {
      DB.withConnection(dataSource.getName) { implicit connection =>
        FIND_BY_USERNAME_QUERY.on("wantedValue" -> username).as[Option[User.Full]](UserRowMapper.full.singleOpt)
      }
    }

  def findByCredentials(credentials: LoginCredentials): Option[User.Full] =
  {

    DB.withConnection(dataSource.getName) { implicit connection =>
      FIND_BY_CREDENTIALS_QUERY.on(
        "username" -> credentials.username,
        "password" -> credentials.password
      ).as[Option[User.Full]](UserRowMapper.full.singleOpt)
    }
  }

  def changePassword(credentials: LoginCredentials)
  {
    DB.withConnection(dataSource.getName) { implicit connection =>
      CHANGE_PASSWORD_QUERY.on(
        "username"                  -> credentials.username,
        "password"                  -> credentials.password,
        "changePasswordOnNextLogin" -> false
      )
        .executeUpdate()
    }
  }
}

  object UserDaoImpl {
    val INSERT_USER_QUERY: SqlQuery = SQL(
      """
      | INSERT INTO users
      |   (username,   password,   first_name,  last_name,  email)
      | VALUES
      |   ({username}, {password}, {firstName}, {lastName}, {email})
    """.
        stripMargin)

  val UPDATE_USER_QUERY: SqlQuery = SQL(
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

  val CHANGE_PASSWORD_QUERY =  SQL(
    """
      | UPDATE users
      | SET
      |   password={password},
      |   change_password_on_next_login={changePasswordOnNextLogin}
      | WHERE
      | username={username}
    """.stripMargin)

  val FIND_ALL_QUERY: SqlQuery = SQL(SELECT_FROM_USERS + "ORDER BY first_name ASC, last_name ASC, middle_name ASC")

  val FIND_BY_ID_QUERY: SqlQuery = SQL(SELECT_FROM_USERS + " WHERE users.id={wantedValue}")

  val FIND_BY_USERNAME_QUERY: SqlQuery = SQL(SELECT_FROM_USERS + " WHERE users.username={wantedValue}")

  val FIND_BY_CREDENTIALS_QUERY: SqlQuery = SQL(SELECT_FROM_USERS + " WHERE users.username={username} AND users.password={password}")

  }

