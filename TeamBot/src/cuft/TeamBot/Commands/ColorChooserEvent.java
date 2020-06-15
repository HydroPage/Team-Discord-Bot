package cuft.TeamBot.Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.awt.Color.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class ColorChooserEvent extends ListenerAdapter
{
    private final Map<String, Color> emoteColors = new HashMap<>();

    public ColorChooserEvent()
    {
        emoteColors.put("🟥", RED);
        emoteColors.put("🟦", BLUE);
        emoteColors.put("🟩", GREEN);
        emoteColors.put("🟨", YELLOW);
        emoteColors.put("🟪", MAGENTA);
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event)
    {
        final JDA jda = event.getJDA();
        final SelfUser self = jda.getSelfUser();

        final User user = event.getUser();

        if (user.equals(self))
        {
            return;
        }

        final Member member = event.getMember();
        final List<Role> memberRoles = member.getRoles();

        final ReactionEmote reaction = event.getReactionEmote();
        final String reactionName = reaction.getName();
        final TextChannel channel = event.getChannel();


        if (emoteColors.containsKey(reactionName))
        {
            final Color selectedColor = emoteColors.get(reactionName);

            final AuditableRestAction<Void> deleteMessage = channel.deleteMessageById(event.getMessageId());

            final String teamName = getTeamRole(memberRoles).getName();
            final String creatorName = member.getEffectiveName();

            final MessageAction announceCreation =
                    channel.sendMessage(formatAnnouncement(teamName, creatorName));

            memberRoles.stream()
                    .filter(role -> role.getName().contains("Team"))
                    .map(Role::getManager)
                    .forEach(manager ->
                            manager.setColor(selectedColor)
                                    .flatMap(success -> deleteMessage)
                                    .flatMap(success -> announceCreation)
                                    .queue()
                    );
        }
    }

    private String formatAnnouncement(String teamName, String actorName)
    {
        return format("```%s Successfully Created By %s!```", teamName, actorName);
    }

    private Role getTeamRole(List<Role> roles)
    {
        return roles.stream()
                .filter(role -> role.getName().matches("Team \\w+$"))
                .collect(toList())
                .get(0);
    }
}
