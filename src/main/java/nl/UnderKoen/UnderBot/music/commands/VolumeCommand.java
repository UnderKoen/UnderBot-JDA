package nl.UnderKoen.UnderBot.music.commands;

import net.dv8tion.jda.core.entities.Member;
import nl.UnderKoen.UnderBot.Roles;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.music.MusicHandler;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;

/**
 * Created by Under_Koen on 10-05-17.
 */
public class VolumeCommand implements Command {
    private String command = "volume";
    private String usage = "volume";
    private String description = "Set the bot volume.";

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
    public int getMinimumRole() {
        return Roles.SUPPORTER.role;
    }

    @Override
    public void setup() throws Exception {
    }

    @Override
    public void run(CommandContext context) {
        int volume = 0;
        try {
            volume = Integer.parseInt(context.getArgs()[0]);
        } catch (Exception e) {
            new ErrorMessage(context.getUser(), context.getArgs()[0] + " is no valid integer").sendMessage(context.getChannel());
            return;
        }
        new TextMessage().addText("Set volume to " + volume + "%").setMention(context.getUser()).sendMessage(context.getChannel());
        MusicHandler.setVolume(context.getGuild(), volume);
    }
}