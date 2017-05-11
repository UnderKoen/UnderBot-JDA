package nl.UnderKoen.UnderBot.music.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.music.MusicHandler;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;

/**
 * Created by Under_Koen on 10-05-17.
 */
public class QueueCommand implements Command {
    private String command = "queue";
    private String usage = "queue";
    private String description = "Outputs the queue of the bot";

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
        AudioTrack[] tracks = MusicHandler.getQueue(context.getGuild());
        AudioTrack current = MusicHandler.getCurrentTrack(context.getGuild());
        if (tracks.length == 0 && current == null) {
            new TextMessage().setMention(context.getUser()).addText("The queue is empty").sendMessage(context.getChannel());
            return;
        }
        TextMessage msg = new TextMessage().setMention(context.getUser()).addText("The queue is:");
        msg.addField("current", "[" + current.getInfo().title + "](" + current.getInfo().uri + ")", false);
        int count = 1;
        for (AudioTrack track: tracks) {
            msg.addField(count + "th", "[" + track.getInfo().title + "](" + track.getInfo().uri + ")", false);
            count++;
        }
        msg.sendMessage(context.getChannel());
    }
}