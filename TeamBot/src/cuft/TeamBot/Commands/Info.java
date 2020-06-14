package cuft.TeamBot.Commands;

import cuft.TeamBot.TeamBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Info extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(TeamBot.PREFIX + "info"))
        {
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("Team Bot");
            info.setDescription("Acidic League Team Bot");
            info.setColor(0xd15f26);
            info.setFooter("Created by Cuft", event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(info.build()).queue();
            info.clear();
        }
    }
}
