package nl.UnderKoen.UnderBot.music.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.User;
import nl.UnderKoen.UnderBot.Roles;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.music.MusicHandler;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Under_Koen on 12-05-17.
 */
public class NextCommand implements Command {
    private String command = "next";
    private String usage = "next";
    private String description = "Vote to skip this song.";

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

    public static List<User> votes = new ArrayList<User>();

    @Override
    public void run(CommandContext context) {
        if (context.getMember().getVoiceState().getChannel() != context.getGuild().getSelfMember().getVoiceState().getChannel()) {
            new ErrorMessage(context.getUser(), "You need to be in " + context.getGuild().getSelfMember().getVoiceState().getChannel().getName()).sendMessage(context.getChannel());
            return;
        }
        if (!MusicHandler.isPlayingMusic(context.getGuild())) {
            new ErrorMessage(context.getUser(), "Bot isn't playing music").sendMessage(context.getChannel());
            return;
        }
        if (MusicHandler.isPlayingDefaultMusic(context.getGuild())) {
            new ErrorMessage(context.getUser(), "Can't skip default song").sendMessage(context.getChannel());
            return;
        }
        if (votes.contains(context.getUser())) {
            new ErrorMessage(context.getUser(), "You already voted").sendMessage(context.getChannel());
            return;
        }
        votes.add(context.getUser());
        AudioTrack track = MusicHandler.getCurrentTrack(context.getGuild());
        new TextMessage().setMention(context.getUser()).addText(context.getMember().getEffectiveName() +
                " voted to skip [" + track.getInfo().title + "] (" + track.getInfo().uri + ") (" +
                votes.size() + "/" + (Math.round(Double.parseDouble(context.getMember().getVoiceState().getChannel().getMembers().size() + "")/2.0) + ")"))
                .sendMessage(context.getChannel());
        if (votes.size() >= (Math.round(Double.parseDouble(context.getMember().getVoiceState().getChannel().getMembers().size() + "")/2.0))) {
            new TextMessage().setMention(context.getUser()).addText("Skipped [" + track.getInfo().title + "](" + track.getInfo().uri + ")").sendMessage(context.getChannel());
            MusicCommand.musicHandler.skipTrack(context.getGuild());
        }
    }
}
