package nl.UnderKoen.UnderBot.music.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import nl.UnderKoen.UnderBot.Roles;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.music.GuildMusicManager;
import nl.UnderKoen.UnderBot.music.MusicHandler;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;
import nl.UnderKoen.UnderBot.utils.RoleUtil;

/**
 * Created by Under_Koen on 10-05-17.
 */
public class PlayCommand implements Command {
    private String command = "play";
    private String usage = "/music play [url]";
    private String description = "Let the bot play the song";

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
                int role = RoleUtil.getHighestRole(context.getMember()).getPosition();
                int durationInMinutes = Math.round(track.getDuration() / 1000 / 60);
                if (role < Roles.MOD.role) {
                    if (MusicHandler.getQueue(context.getGuild()).length >= 10) {
                        new TextMessage().setMention(context.getUser()).addText(MusicHandler.getQueue(context.getGuild()).length + " is the max queue lenght for your role.").sendMessage(context.getChannel());
                        return;
                    }
                    if (durationInMinutes > ((role == Roles.SUPPORTER.role) ? 15 : 5)) {
                        new TextMessage().setMention(context.getUser()).addText(durationInMinutes + " minutes is to long for your role.").sendMessage(context.getChannel());
                        return;
                    }
                }
                MusicCommand.musicHandler.playTrack(context.getGuild(), musicManager, track, context);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                new ErrorMessage(context.getUser(), "Use /music playlist for playing a playlist").sendMessage(context.getChannel());
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