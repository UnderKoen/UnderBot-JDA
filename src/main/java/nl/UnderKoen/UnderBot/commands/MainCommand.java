package nl.UnderKoen.UnderBot.commands;

import nl.UnderKoen.UnderBot.commands.moderator.TimeoutCommand;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.entities.impl.CommandContextImpl;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;
import nl.UnderKoen.UnderBot.utils.Messages.HelpMessage;
import nl.UnderKoen.UnderBot.utils.RoleUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Under_Koen on 10-05-17.
 */
public interface MainCommand extends Command {
    List<Command> getSubcommands();

    @Override
    default void setup() throws Exception {
        List<Command> commands = getSubcommands();
        for (Command command: commands) {
            command.setup();
        }
    }

    @Override
    default void run(CommandContext context) throws Exception {
        List<Command> commands = getSubcommands();
        if (context.getArgs().length == 0) {
            new HelpMessage().addSubCommands(commands).addText("All availble subcommands for /" + getCommand()).sendMessage(context.getChannel());
        } else {
            for (Command command: commands) {
                if (command.getCommand().contentEquals(context.getArgs()[0])) {
                    if (RoleUtil.getHighestRole(context.getMember()).getPosition() < command.getMinimumRole()) {
                        new ErrorMessage(context.getUser(), "The minimum role for /" + command.getCommand() + " is " +
                                RoleUtil.getRole(context.getGuild(), command.getMinimumRole()).getName()).sendMessage(context.getChannel());
                        context.getMessage().delete().complete();
                        return;
                    }
                    List<String> newArgs = Arrays.asList(context.getArgs());
                    newArgs = new ArrayList<>(newArgs);
                    newArgs.remove(0);

                    List<String> newRawArgs = Arrays.asList(context.getRawArgs());
                    newRawArgs = new ArrayList<>(newRawArgs);
                    newRawArgs.remove(0);
                    command.run(new CommandContextImpl()
                            .setArgs(newArgs.toArray(new String[0]))
                            .setRawArgs(newRawArgs.toArray(new String[0]))
                            .setChannel(context.getChannel())
                            .setCommand(command.getCommand())
                            .setGuild(context.getGuild())
                            .setMember(context.getMember())
                            .setUser(context.getUser())
                            .setPrefix(context.getPrefix())
                            .setMessage(context.getMessage())
                    );
                    return;
                }
            }
            new HelpMessage().addSubCommands(commands).addText("All availble subcommands for /" + getCommand()).sendMessage(context.getChannel());
        }
    }
}
