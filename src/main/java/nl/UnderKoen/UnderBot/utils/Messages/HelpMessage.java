package nl.UnderKoen.UnderBot.utils.Messages;

import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import nl.UnderKoen.UnderBot.Main;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.utils.ColorUtil;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Under_Koen on 21-04-17.
 */
public class HelpMessage implements UnderMessage {

    private Color color = ColorUtil.hexToColor("#2a6886");

    private User user;

    private String message = "All currently available commands are:";

    private List<MessageEmbed.Field> fields;

    public HelpMessage(ArrayList<Command> commands, User author) {
        user = author;
        fields = new ArrayList<MessageEmbed.Field>();
        for (Command command : Main.handler.getAllCommands()) {
            fields.add(new MessageEmbed.Field(command.getUsage(), command.getDescription(), true));
        }
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public User getAuthor() {
        return user;
    }

    @Override
    public String getDescription() {
        return message;
    }

    @Override
    public List<MessageEmbed.Field> getFields() {
        return fields;
    }
}