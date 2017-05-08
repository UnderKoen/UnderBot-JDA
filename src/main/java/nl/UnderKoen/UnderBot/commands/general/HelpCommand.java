package nl.UnderKoen.UnderBot.commands.general;

import nl.UnderKoen.UnderBot.Main;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.utils.Messages.HelpMessage;

/**
 * Created by Under_Koen on 21-04-17.
 */
public class HelpCommand implements Command {
    private String command = "help";
    private String usage = "/help";
    private String description = "Returns all commands.";

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
        new HelpMessage()
                .setMention(context.getUser())
                .addText("All available commands are:")
                .addCommands(Main.handler.getAllCommands())
                .sendMessage(context.getChannel());
    }
}
