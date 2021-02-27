package net.seichi915.seichi915bot.command

import com.jagrosh.jdautilities.command.{Command, CommandEvent}
import net.dv8tion.jda.api.EmbedBuilder
import net.seichi915.seichi915bot.Seichi915Bot
import net.seichi915.seichi915bot.database.Database

import java.awt.Color
import java.util.logging.Level
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Random, Success}

class Seichi915AccountCommand extends Command {
  this.name = "seichi915account"

  private val symbolList = List(
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
    'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
    'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
    'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7',
    '8', '9'
  )

  override def execute(event: CommandEvent): Unit =
    Database.getId(event.getAuthor.getIdLong) onComplete {
      case Success(value) =>
        var id = ""
        if (value.nonEmpty) {
          id = value.get
          val embed = new EmbedBuilder()
            .setTitle("seichi915Account")
            .setDescription("連携用URLをダイレクトメッセージに送信しました。")
            .build()
          event.getChannel.sendMessage(embed).queue()
          event.getAuthor
            .openPrivateChannel()
            .flatMap(_.sendMessage(
              s"次のURLにアクセスしてください: \nhttps://form.seichi915.net/discord/$id/"))
            .queue()
        } else {
          (0 to 128).foreach { _ =>
            id += symbolList(Random.nextInt(symbolList.size))
          }
          Database.setId(event.getAuthor.getIdLong, id) onComplete {
            case Success(_) =>
              val embed = new EmbedBuilder()
                .setTitle("seichi915Account")
                .setDescription("連携用URLをダイレクトメッセージに送信しました。")
                .build()
              event.getChannel.sendMessage(embed).queue()
              event.getAuthor
                .openPrivateChannel()
                .flatMap(_.sendMessage(
                  s"次のURLにアクセスしてください: \nhttps://form.seichi915.net/discord/$id/"))
                .queue()
            case Failure(exception) =>
              Seichi915Bot.getLogger.log(Level.SEVERE,
                                         "データベースの操作中にエラーが発生しました。",
                                         exception)
              val embed = new EmbedBuilder()
                .setTitle("seichi915Account")
                .setDescription("データベースの操作中にエラーが発生しました。")
                .setColor(Color.RED)
                .build()
              event.getChannel.sendMessage(embed).queue()
          }
        }
      case Failure(exception) =>
        Seichi915Bot.getLogger.log(Level.SEVERE,
                                   "データベースの操作中にエラーが発生しました。",
                                   exception)
        val embed = new EmbedBuilder()
          .setTitle("seichi915Account")
          .setDescription("データベースの操作中にエラーが発生しました。")
          .setColor(Color.RED)
          .build()
        event.getChannel.sendMessage(embed).queue()
    }
}
