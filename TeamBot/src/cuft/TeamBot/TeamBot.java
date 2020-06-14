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

import static net.dv8tion.jda.api.OnlineStatus.ONLINE;
import static net.dv8tion.jda.api.entities.Activity.playing;
import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_MEMBERS;

public class TeamBot {
	public static final String PREFIX = "*";

	public static void main(String[] args) throws LoginException, InterruptedException
	{
		JDABuilder.createDefault("token")
				.enableIntents(GUILD_MEMBERS)
				.setActivity(playing("Acidic League"))
				.setStatus(ONLINE)
				.addEventListeners(new Info(), new TeamCommands(), new ColorChooserEvent())
				.build()
				.awaitReady();
	}
}
