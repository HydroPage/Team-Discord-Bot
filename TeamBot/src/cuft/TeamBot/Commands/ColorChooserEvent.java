package cuft.TeamBot.Commands;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ColorChooserEvent extends ListenerAdapter
{

    public void changeTeamRolesColor(List<Role> teamRoles, Color clr)
    {
        for (Role role : teamRoles)
        {
            role.getManager().setColor(clr).queue();
        }
    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event)
    {
        Utility util = new Utility();

        //All of the user's roles
        List<Role> allUserRoles = event.getMember().getRoles();

        //red
        if (event.getReactionEmote().getName().equals("🟥") && !event.getMember().getUser().equals(event.getJDA().getSelfUser()))
        {
            changeTeamRolesColor(util.allUsersTeamRoles(allUserRoles), Color.red);

            event.getChannel().deleteMessageById(event.getMessageId()).queue();
            event.getChannel().sendMessage("```" + util.getUserTeam(allUserRoles).getName() + " Successfully Created By " + event.getUser().getName() + "!```").queue();
        }
        //blue
        else if (event.getReactionEmote().getName().equals("🟦") && !event.getMember().getUser().equals(event.getJDA().getSelfUser()))
        {
            changeTeamRolesColor(util.allUsersTeamRoles(allUserRoles), Color.blue);

            event.getChannel().deleteMessageById(event.getMessageId()).queue();
            event.getChannel().sendMessage("```" + util.getUserTeam(allUserRoles).getName() + " Successfully Created By " + event.getUser().getName() + "!```").queue();
        }
        //green
        else if (event.getReactionEmote().getName().equals("🟩") && !event.getMember().getUser().equals(event.getJDA().getSelfUser()))
        {
            changeTeamRolesColor(util.allUsersTeamRoles(allUserRoles), Color.green);

            event.getChannel().deleteMessageById(event.getMessageId()).queue();
            event.getChannel().sendMessage("```" + util.getUserTeam(allUserRoles).getName() + " Successfully Created By " + event.getUser().getName() + "!```").queue();
        }
        //yellow
        else if (event.getReactionEmote().getName().equals("🟨") && !event.getMember().getUser().equals(event.getJDA().getSelfUser()))
        {
            changeTeamRolesColor(util.allUsersTeamRoles(allUserRoles), Color.yellow);

            event.getChannel().deleteMessageById(event.getMessageId()).queue();
            event.getChannel().sendMessage("```" + util.getUserTeam(allUserRoles).getName() + " Successfully Created By " + event.getUser().getName() + "!```").queue();
        }
        //purple
        else if (event.getReactionEmote().getName().equals("🟪") && !event.getMember().getUser().equals(event.getJDA().getSelfUser()))
        {
            changeTeamRolesColor(util.allUsersTeamRoles(allUserRoles), Color.MAGENTA);

            event.getChannel().deleteMessageById(event.getMessageId()).queue();
            event.getChannel().sendMessage("```" + util.getUserTeam(allUserRoles).getName() + " Successfully Created By " + event.getUser().getName() + "!```").queue();
        }


    }
}
