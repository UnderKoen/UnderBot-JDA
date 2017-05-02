package nl.UnderKoen.UnderBot.commands.general;

import nl.UnderKoen.UnderBot.Main;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;

/**
 * Created by Under_Koen on 21-04-17.
 */
public class InfoCommand implements Command {
    private String command = "info";
    private String usage = "/info";
    private String description = "Returns the bot version.";

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
        new TextMessage().addMention(context.getUser())
                .addText("The current version of the bot is: " + Main.version)
                .addText("Use /changelog for the more detail")
                .sendMessage(context.getChannel());
    }
}
