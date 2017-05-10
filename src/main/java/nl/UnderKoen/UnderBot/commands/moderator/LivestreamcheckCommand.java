package nl.UnderKoen.UnderBot.commands.moderator;

import net.dv8tion.jda.core.entities.Member;
import nl.UnderKoen.UnderBot.Roles;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.threads.Livestreamcheck;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Under_Koen on 09-05-17.
 */
public class LivestreamcheckCommand implements Command {
    private String command = "livestreamcheck";
    private String usage = "/livestreamcheck [boolean] (textChannel)]";
    private String description = "Enable/disable livestreamcheck.\n If textChannel is empty it outputs in your current channel.";
    private int minimumRole = Roles.MOD.role;

    @Override
    public int getMinimumRole() {
        return minimumRole;
    }
    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setup() throws Exception {
    }

    @Override
    public void run(CommandContext context) throws Exception {
        if (context.getRawArgs().length < 1) {
            new ErrorMessage(context.getUser(), "This command needs arguments to work.")
                    .sendMessage(context.getChannel());
            return;
        }
        if (!(context.getArgs()[0].toLowerCase().contains("true") || context.getArgs()[0].toLowerCase().contains("false"))) {
            new ErrorMessage(context.getUser(), context.getArgs()[0] + " is not a valid boolean")
                    .sendMessage(context.getChannel());
            return;
        }
        Livestreamcheck.Check = Boolean.parseBoolean(context.getArgs()[0]);
        if (!Livestreamcheck.Check) return;
        if (context.getArgs().length >= 2) {
            Pattern pattern = Pattern.compile("<#(\\d+)>");
            Matcher matcher = pattern.matcher(context.getRawArgs()[1]);
            matcher.find();
            try {
                Livestreamcheck.channel = context.getGuild().getTextChannelById(matcher.group(1));
            } catch (Exception e) {
                new ErrorMessage(context.getUser(), context.getRawArgs()[1] + " is not a valid channel.")
                        .sendMessage(context.getChannel());
                return;
            }
        } else {
            Livestreamcheck.channel = context.getChannel();
        }
        new Livestreamcheck().start();
        if (Livestreamcheck.Check) {
            new TextMessage().setMention(context.getUser())
                    .addText("Enabled livestream check for " + Livestreamcheck.channel.getAsMention() + ".")
                    .sendMessage(context.getChannel());
        } else {
            new TextMessage().setMention(context.getUser())
                    .addText("Disabled livestream check for " + Livestreamcheck.channel.getAsMention() + ".")
                    .sendMessage(context.getChannel());
        }

    }
}
