package nl.UnderKoen.UnderBot.commands;

import nl.UnderKoen.UnderBot.entities.CommandContext;
import sun.plugin2.message.Message;

import java.sql.Timestamp;

/**
 * Created by Under_Koen on 19-04-17.
 */
public interface Command {
    public String getCommand();

    public void run(CommandContext context);
}
