package nl.UnderKoen.UnderBot.music.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Member;
import nl.UnderKoen.UnderBot.Roles;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.music.GuildMusicManager;
import nl.UnderKoen.UnderBot.music.MusicHandler;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;
import nl.UnderKoen.UnderBot.utils.RoleUtil;

/**
 * Created by Under_Koen on 11-05-17.
 */
public class DefaultCommand implements Command {
    private String command = "default";
    private String usage = "default [url]";
    private String description = "If the bot runs out of songs plays this song until a song has been added.";

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
        if (context.getArgs().length == 0) {
            new ErrorMessage(context.getUser(), "This command needs arguments to work").sendMessage(context.getChannel());
            return;
        }
        if (!context.getGuild().getSelfMember().getVoiceState().inVoiceChannel()) {
            new ErrorMessage(context.getUser(), "Bot needs to be in a voice channel").sendMessage(context.getChannel());
            return;
        }
        GuildMusicManager musicManager = MusicCommand.musicHandler.getGuildAudioPlayer(context.getGuild());

        String url = context.getRawArgs()[0];

        MusicCommand.musicHandler.playerManager.loadItemOrdered(musicManager, url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                MusicCommand.musicHandler.setDefaultTrack(context.getGuild(), musicManager, track);
                new TextMessage().setMention(context.getUser()).addText("Set the default track to [" + track.getInfo().title + "](" + track.getInfo().uri + ")").sendMessage(context.getChannel());
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                new ErrorMessage(context.getUser(), "Playlist support not added yet").sendMessage(context.getChannel());
            }

            @Override
            public void noMatches() {
                new ErrorMessage(context.getUser(), "Couldn't find " + url).sendMessage(context.getChannel());
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                new ErrorMessage(context.getUser(), "The load of " + url + " was not succesfull.").sendMessage(context.getChannel());
            }
        });
    }
}