package net.seichi915.seichi915bot.command

import com.jagrosh.jdautilities.command.{Command, CommandEvent}

class PingCommand extends Command {
  this.name = "ping"

  override def execute(event: CommandEvent): Unit =
    event.getChannel.sendMessage("Pong").queue()
}
