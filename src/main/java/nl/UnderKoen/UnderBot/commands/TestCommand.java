package nl.UnderKoen.UnderBot.commands;

import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.utils.messages.TextMessage;

import java.awt.*;

/**
 * Created by Under_Koen on 19-04-17.
 */
public class TestCommand implements Command {
    private String command = "test";

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public void run(CommandContext context) {
        //new ErrorMessage(context.getUser(), "This command needs arguments").sendMessage(context.getChannel());
        new TextMessage(context.getUser(), "test \t hey hey hyehydhyehdy \t egt").sendMessage(context.getChannel());
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }
}
