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

    private String message;

    private List<MessageEmbed.Field> fields;

    public HelpMessage setMention(User user) {
        this.user = user;
        return this;
    }

    public HelpMessage addText(String text) {
        if (message == null) {
            message = "";
        } else {
            message = message + "\n";
        }
        message = message + text;
        return this;
    }

    public HelpMessage addCommand(Command command) {
        if (fields == null) {
            fields = new ArrayList<MessageEmbed.Field>();
        }
        fields.add(new MessageEmbed.Field(command.getUsage(), command.getDescription(), false));
        return this;
    }

    public HelpMessage addCommands(List<Command> commands) {
        for (Command command: commands) {
            addCommand(command);
        }
        return this;
    }

    public HelpMessage addSubCommand(Command subCommand) {
        if (fields == null) {
            fields = new ArrayList<MessageEmbed.Field>();
        }
        fields.add(new MessageEmbed.Field(subCommand.getUsage().replaceFirst(Main.handler.getPrefix(),""), subCommand.getDescription(), false));
        return this;
    }

    public HelpMessage addSubCommands(List<Command> subCommands) {
        for (Command command: subCommands) {
            addSubCommand(command);
        }
        return this;
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