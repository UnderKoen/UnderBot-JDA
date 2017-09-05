package nl.UnderKoen.UnderBot.minesweeper.commands;

import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.minesweeper.Map;
import nl.UnderKoen.UnderBot.minesweeper.Minesweeper;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;

/**
 * Created by Under_Koen on 21-07-17.
 */
public class CheckCommand implements Command {
    private String command = "check";
    private String usage = "/check";
    private String description = "Checks if you completed your game.";

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
        if (ms.getMap().isFinished()) {
            new TextMessage().addText("You finished the game concrats.").setTitle("MineSweeper").setMention(context.getUser()).sendMessage(context.getChannel());
        } else {
            new TextMessage().addText("You didn't finished try to flag all bombs.").setTitle("MineSweeper").setMention(context.getUser()).sendMessage(context.getChannel());
        }
    }
}
