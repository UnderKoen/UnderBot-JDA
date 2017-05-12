package nl.UnderKoen.UnderBot.music.commands;

import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.commands.MainCommand;
import nl.UnderKoen.UnderBot.music.MusicHandler;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Under_Koen on 10-05-17.
 */
public class MusicCommand implements MainCommand {
    private String command = "music";
    private String[] aliases = {"m"};
    private String usage = "/music [subcommand]";
    private String description = "This is the main music command.";
    private Command[] subcommands = {new PlayCommand(), new PlaylistCommand(), new QueueCommand(), new NextCommand(), new ForceNextCommand(),
            new JoinCommand(), new LeaveCommand(), new DefaultCommand(), new VolumeCommand()};

    public static MusicHandler musicHandler;

    public MusicCommand() {
        musicHandler = new MusicHandler();
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
    public String[] getAliases() {
        return aliases;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<Command> getSubcommands() {
        return Arrays.asList(subcommands);
    }
}