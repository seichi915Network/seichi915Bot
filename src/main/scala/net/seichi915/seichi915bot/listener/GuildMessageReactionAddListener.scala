package net.seichi915.seichi915bot.listener

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.seichi915.seichi915bot.configuration.Configuration

import scala.jdk.CollectionConverters._

class GuildMessageReactionAddListener extends ListenerAdapter {
  override def onGuildMessageReactionAdd(
      event: GuildMessageReactionAddEvent): Unit =
    if (event.getChannel.getIdLong.equals(Configuration.getRoleBotChannelId)) {
      val roleId = event.getReactionEmote.getName match {
        case "1️⃣" => Configuration.getRoleBotRoleId(1)
        case "2️⃣" => Configuration.getRoleBotRoleId(2)
        case "3️⃣" => Configuration.getRoleBotRoleId(3)
        case "4️⃣" => Configuration.getRoleBotRoleId(4)
        case "5️⃣" => Configuration.getRoleBotRoleId(5)
        case "6️⃣" => Configuration.getRoleBotRoleId(6)
        case "7️⃣" => Configuration.getRoleBotRoleId(7)
        case "8️⃣" => Configuration.getRoleBotRoleId(8)
        case "9️⃣" => Configuration.getRoleBotRoleId(9)
        case _     => return
      }
      if (!event.getMember.getRoles.asScala
            .map(_.getIdLong)
            .toSet
            .contains(roleId))
        event.getGuild
          .addRoleToMember(event.getMember, event.getGuild.getRoleById(roleId))
          .queue()
    } else if (event.getChannel.getIdLong.equals(
                 Configuration.getWelcomeChannelId)) {
      val roleId = Configuration.getMemberRoleId
      if (!event.getMember.getRoles.asScala
            .map(_.getIdLong)
            .toSet
            .contains(roleId))
        event.getGuild
          .addRoleToMember(event.getMember, event.getGuild.getRoleById(roleId))
          .queue()
    }
}
