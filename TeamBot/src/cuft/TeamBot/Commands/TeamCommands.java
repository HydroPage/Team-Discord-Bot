package cuft.TeamBot.Commands;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;

import cuft.TeamBot.TeamBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class TeamCommands extends ListenerAdapter
{

    public String createTeamName(String[] usersMessage)
    {
        String name = "";
        for (int i = 1; i < usersMessage.length; i++)
        {
            name += usersMessage[i] + " ";
        }
        return name.trim();
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        Utility util = new Utility();
        final Guild guild = event.getGuild();
        String[] args = event.getMessage().getContentRaw().split(" ");
        final String teamName = createTeamName(args);

        List<Role> allUserRoles = event.getMember().getRoles();

        if (args[0].equalsIgnoreCase(TeamBot.prefix + "create"))
        {
            if (util.isOnTeam(allUserRoles))
            {
                event.getChannel().sendMessage("You Are Already On A Team!").queue();
            }
            else if (args.length <= 1)
            {
                event.getChannel().sendMessage("You Must Enter In A Team Name!").queue();
            }
            else if (util.teamExists(guild.getRoles(), teamName))
            {
                event.getChannel().sendMessage("This Team Already Exists!").queue();
            }
            else
            {
                //Creates team role and captain role and adds its to the player
                guild.createRole().setName("Team " + teamName).queue(role ->
                        guild.addRoleToMember(event.getMember(), role).queue());

                guild.createRole().setName("Team " + teamName + " Captain").queue(role ->
                        guild.addRoleToMember(event.getMember(), role).queue());

                //Color chooser pops up right after
                EmbedBuilder colorChooser = new EmbedBuilder();
                colorChooser.setTitle("Choose A Team Color");
                colorChooser.setColor(Color.black);

                event.getChannel().sendMessage(colorChooser.build()).queue(new Consumer<Message>()
                {
                    @Override
                    public void accept(Message message)
                    {
                        //red
                        message.addReaction("🟥").queue();
                        //blue
                        message.addReaction("🟦").queue();
                        //green
                        message.addReaction("🟩").queue();
                        //yellow
                        message.addReaction("🟨").queue();
                        //purple
                        message.addReaction("🟪").queue();
                    }
                });
                colorChooser.clear();
            }
        }

        //Delete command
        if (args[0].equalsIgnoreCase(TeamBot.prefix + "delete"))
        {
            if (!util.isOnTeam(allUserRoles))
            {
                event.getChannel().sendMessage("You Are Not On A Team!").queue();
            }
            else if (!util.isCaptain(allUserRoles))
            {
                event.getChannel().sendMessage("You Are Not The Captain Of Your Team!").queue();
            }
            else
            {
                String tName = util.getUserTeam(allUserRoles).getName();
                for (Role role : util.allUsersTeamRoles(allUserRoles))
                {
                    role.delete().queue();
                }

                event.getChannel().sendMessage("```" + tName + " Successfully Deleted By " + event.getMember().getUser().getName() + "!```").queue();
            }
        }

        //Invite command
    }
}
