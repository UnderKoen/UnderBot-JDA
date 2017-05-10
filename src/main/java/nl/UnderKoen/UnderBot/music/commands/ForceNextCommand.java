package nl.UnderKoen.UnderBot.music.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import nl.UnderKoen.UnderBot.Roles;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.music.MusicHandler;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;

/**
 * Created by Under_Koen on 10-05-17.
 */
public class ForceNextCommand implements Command {
    private String command = "forcenext";
    private String usage = "/music forcenext";
    private String description = "Let the bot skip this song.";

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
        return Roles.MOD.role;
    }

    @Override
    public void setup() throws Exception {
    }

    @Override
    public void run(CommandContext context) {
        if (!MusicHandler.isPlayingMusic(context.getGuild())) {
            new ErrorMessage(context.getUser(), "Bot isn't playing music").sendMessage(context.getChannel());
            return;
        }
        AudioTrack track = MusicHandler.getCurrentTrack(context.getGuild());
        new TextMessage().setMention(context.getUser()).addText("Skipped [" + track.getInfo().title + "](" + track.getInfo().uri + ")").sendMessage(context.getChannel());
        MusicCommand.musicHandler.skipTrack(context.getGuild());
    }
}