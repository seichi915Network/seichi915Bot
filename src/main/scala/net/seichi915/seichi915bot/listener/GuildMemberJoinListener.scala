package net.seichi915.seichi915bot.listener

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.seichi915.seichi915bot.configuration.Configuration

class GuildMemberJoinListener extends ListenerAdapter {
  override def onGuildMemberJoin(event: GuildMemberJoinEvent): Unit =
    event.getGuild
      .getTextChannelById(Configuration.getWelcomeMessageChannelId)
      .sendMessage(
        s"${event.getMember.getAsMention}さんがseichi915Discordに参加しました。")
      .queue()
}
