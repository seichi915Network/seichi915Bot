package net.seichi915.seichi915bot.database

import net.seichi915.seichi915bot.configuration.Configuration
import scalikejdbc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Database {
  Class.forName("com.mysql.jdbc.Driver")

  ConnectionPool.singleton(
    s"jdbc:mysql://${Configuration.getDatabaseHost}:${Configuration.getDatabasePort}/${Configuration.getDatabaseName}",
    Configuration.getDatabaseUsername,
    Configuration.getDatabasePassword
  )

  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(
    enabled = false
  )

  def getId(userId: Long): Future[Option[String]] = Future {
    DB localTx { implicit session =>
      sql"SELECT * FROM discord_ids WHERE user_id = $userId"
        .map(_.string("id"))
        .list()
        .apply()
        .headOption
    }
  }

  def setId(userId: Long, id: String): Future[Unit] = Future {
    DB localTx { implicit session =>
      sql"INSERT INTO discord_ids (user_id, id) VALUES ($userId, $id)"
        .update()
        .apply()
    }
  }
}
