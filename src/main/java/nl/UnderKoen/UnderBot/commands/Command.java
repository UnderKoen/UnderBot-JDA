package nl.UnderKoen.UnderBot.commands;

import nl.UnderKoen.UnderBot.entities.CommandContext;

/**
 * Created by Under_Koen on 19-04-17.
 */
public interface Command {
    String getCommand();

    String getUsage();

    String getDescription();

    default int getMinimumRole() {
        return 0;
    }

    void setup() throws  Exception;

    void run(CommandContext context) throws Exception;
}
