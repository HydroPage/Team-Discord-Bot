package cuft.TeamBot;

import javax.security.auth.login.LoginException;

import cuft.TeamBot.Commands.ColorChooserEvent;
import cuft.TeamBot.Commands.Info;
import cuft.TeamBot.Commands.TeamCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_MEMBERS;

public class TeamBot {
	public static JDA jda;
	public static String prefix = "*";

	public static void main(String args[]) throws LoginException
	{
		jda = JDABuilder.createDefault("token").enableIntents(GUILD_MEMBERS).setActivity(Activity.playing("Acidic League")).build();
		
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		
		jda.addEventListener(new Info());
		jda.addEventListener(new TeamCommands());
		jda.addEventListener(new ColorChooserEvent());
	}
}
