package nl.UnderKoen.UnderBot.commands.general;

import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.utils.YoutubeUtil;

/**
 * Created by Under_Koen on 19-04-17.
 */
public class TestCommand implements Command {
    private String command = "test";
    private String usage = "/test";
    private String description = "This is a test command.";

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
    }
}
