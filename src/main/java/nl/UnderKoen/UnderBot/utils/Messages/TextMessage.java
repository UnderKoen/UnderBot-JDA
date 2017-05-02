package nl.UnderKoen.UnderBot.utils.Messages;

import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import nl.UnderKoen.UnderBot.utils.ColorUtil;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Under_Koen on 21-04-17.
 */
public class TextMessage implements UnderMessage {
    private Color color = ColorUtil.hexToColor("#2a6886");

    private User user;

    private String message;

    private java.util.List<MessageEmbed.Field> fields;

    public TextMessage addMention(User user) {
        this.user = user;
        return this;
    }

    public TextMessage addText(String text) {
        if (message == null) {
            message = "";
        } else {
            message = message + "\n";
        }
        message = message + text;
        return this;
    }

    public TextMessage addField(String name, String value, boolean inline) {
        if (fields == null) {
            fields = new ArrayList<MessageEmbed.Field>();
        }
        fields.add(new MessageEmbed.Field(name, value, inline));
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
