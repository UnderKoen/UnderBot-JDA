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
public class JoinCommand implements Command {
    private String command = "join";
    private String usage = "join";
    private String description = "Let the bot join your channel";

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
        Member member = context.getMember();
        if (!member.getVoiceState().inVoiceChannel()) {
            new ErrorMessage(member.getUser(), "You need to be in a voice channel").sendMessage(context.getChannel());
            return;
        }
        MusicHandler.channel = context.getChannel();
        new TextMessage().addText("Just joined: " + context.getMember()
                .getVoiceState().getChannel().getName()).setMention(context.getUser()).sendMessage(context.getChannel());
        MusicCommand.musicHandler.joinChannel(context.getMember().getVoiceState().getChannel());
    }
}