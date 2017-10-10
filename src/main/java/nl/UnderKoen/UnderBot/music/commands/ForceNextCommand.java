package nl.underkoen.underbot.music.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import nl.underkoen.underbot.Roles;
import nl.underkoen.underbot.commands.Command;
import nl.underkoen.underbot.entities.CommandContext;
import nl.underkoen.underbot.music.MusicHandler;
import nl.underkoen.underbot.utils.Messages.ErrorMessage;
import nl.underkoen.underbot.utils.Messages.TextMessage;

/**
 * Created by Under_Koen on 10-05-17.
 */
public class ForceNextCommand implements Command {
    private String command = "forcenext";
    private String usage = "forcenext";
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
        if (context.getMember().getVoiceState().getChannel() != context.getGuild().getSelfMember().getVoiceState().getChannel()) {
            new ErrorMessage(context.getMember(), "You need to be in " + context.getGuild().getSelfMember().getVoiceState().getChannel().getName()).sendMessage(context.getChannel());
            return;
        }
        if (!MusicHandler.isPlayingMusic(context.getGuild())) {
            new ErrorMessage(context.getMember(), "Bot isn't playing music").sendMessage(context.getChannel());
            return;
        }
        if (MusicHandler.isPlayingDefaultMusic(context.getGuild())) {
            new ErrorMessage(context.getMember(), "Can't skip default song").sendMessage(context.getChannel());
            return;
        }
        AudioTrack track = MusicHandler.getCurrentTrack(context.getGuild());
        new TextMessage().setMention(context.getMember()).addText("Skipped [" + track.getInfo().title + "](" + track.getInfo().uri + ")").sendMessage(context.getChannel());
        MusicCommand.musicHandler.skipTrack(context.getGuild());
    }
}