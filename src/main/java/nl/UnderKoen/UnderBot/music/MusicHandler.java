package nl.UnderKoen.UnderBot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;
import nl.UnderKoen.UnderBot.Main;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.music.commands.MusicCommand;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Under_Koen on 10-05-17.
 */
public class MusicHandler extends ListenerAdapter {
    public static AudioPlayerManager playerManager;
    public static Map<Long, GuildMusicManager> musicManagers;

    public static TextChannel channel;

    public MusicHandler() {
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public static synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void skipTrack(Guild guild) {
        GuildMusicManager musicManager = getGuildAudioPlayer(guild);
        musicManager.scheduler.nextTrack();
    }

    public void playTrack(Guild guild, GuildMusicManager musicManager, AudioTrack track, CommandContext context) {
        musicManager.scheduler.queue(track, context);
    }

    public void joinChannel(VoiceChannel channel) {
        AudioManager audioManager = channel.getGuild().getAudioManager();
        audioManager.openAudioConnection(channel);
    }

    public void leave(Guild guild) {
        AudioManager audioManager = guild.getAudioManager();
        audioManager.closeAudioConnection();
        getGuildAudioPlayer(guild).scheduler.clearQueue();
        getGuildAudioPlayer(guild).player.stopTrack();
    }

    public static boolean isPlayingMusic(Guild guild) {
        return getCurrentTrack(guild) != null;
    }

    public static boolean isPlayingDefaultMusic(Guild guild) {
        return getCurrentTrack(guild) == getGuildAudioPlayer(guild).scheduler.defaultTrack;
    }

    public static boolean hasDefaultMusic(Guild guild) {
        return getGuildAudioPlayer(guild).scheduler.defaultTrack != null;
    }

    public void setDefaultTrack(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.setDefault(track);
    }

    public static AudioTrack getCurrentTrack(Guild guild) {
        return getGuildAudioPlayer(guild).player.getPlayingTrack();
    }

    public static AudioTrack getDefaultTrack(Guild guild) {
        return getGuildAudioPlayer(guild).scheduler.defaultTrack;
    }

    public static void setVolume(Guild guild, int volume) {
        getGuildAudioPlayer(guild).player.setVolume(volume);
    }

    public static AudioTrack[] getQueue(Guild guild) {
        return getGuildAudioPlayer(guild).scheduler.getQueue();
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        Guild guild = event.getGuild();
        if (!isPlayingMusic(guild)) return;
        VoiceChannel channel = event.getChannelJoined();
        if (guild.getSelfMember().getVoiceState().getChannel() == channel) {
            getGuildAudioPlayer(guild).scheduler.setPause(false);
            if (channel.getMembers().size() == 2) {
                new TextMessage().addText("Just unpaused: [" + getGuildAudioPlayer(guild).player.getPlayingTrack().getInfo().title + "](" + getGuildAudioPlayer(guild).player.getPlayingTrack().getInfo().uri + ")").sendMessage(this.channel);
            }
        }
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        Guild guild = event.getGuild();
        if (!isPlayingMusic(guild)) return;
        VoiceChannel channel = event.getChannelLeft();
        if (guild.getSelfMember().getVoiceState().getChannel() == channel) {
            if (channel.getMembers().size() == 1) {
                getGuildAudioPlayer(guild).scheduler.setPause(true);
                new TextMessage().addText("Just paused: [" + getGuildAudioPlayer(guild).player.getPlayingTrack().getInfo().title + "](" + getGuildAudioPlayer(guild).player.getPlayingTrack().getInfo().uri + ")").sendMessage(this.channel);
            }
        }
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        Guild guild = event.getGuild();
        if (!isPlayingMusic(guild)) return;
        VoiceChannel channel = event.getChannelLeft();
        if (guild.getSelfMember().getVoiceState().getChannel() == channel) {
            if (channel.getMembers().size() == 1) {
                getGuildAudioPlayer(guild).scheduler.setPause(true);
                new TextMessage().addText("Just paused: [" + getGuildAudioPlayer(guild).player.getPlayingTrack().getInfo().title + "](" + getGuildAudioPlayer(guild).player.getPlayingTrack().getInfo().uri + ")").sendMessage(this.channel);
            }
        } else if (guild.getSelfMember().getVoiceState().getChannel() == event.getChannelJoined()) {
            getGuildAudioPlayer(guild).scheduler.setPause(false);
            if (event.getChannelJoined().getMembers().size() == 2) {
                new TextMessage().addText("Just unpaused: [" + getGuildAudioPlayer(guild).player.getPlayingTrack().getInfo().title + "](" + getGuildAudioPlayer(guild).player.getPlayingTrack().getInfo().uri + ")").sendMessage(this.channel);
            }
        }
    }
}
