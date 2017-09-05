package nl.UnderKoen.UnderBot.minesweeper.commands;

import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.minesweeper.Map;
import nl.UnderKoen.UnderBot.minesweeper.Minesweeper;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;

/**
 * Created by Under_Koen on 21-07-17.
 */
public class ShowCommand implements Command {
    private String command = "show";
    private String usage = "/show";
    private String description = "Shows your map.";

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
    public void run(CommandContext context) throws Exception {
        Minesweeper ms = Minesweeper.getGame(context.getUser());
        ms.sendMap(context.getChannel());
    }
}