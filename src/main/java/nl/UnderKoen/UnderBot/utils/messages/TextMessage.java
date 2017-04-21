package nl.UnderKoen.UnderBot.utils.messages;

import net.dv8tion.jda.core.entities.MessageEmbed.*;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.util.*;

/**
 * Created by Under_Koen on 21-04-17.
 */
public class TextMessage implements UnderMessage {
    private Color color = Color.BLUE;

    private User user;

    private String message;

    private java.util.List<Field> fields;

    public TextMessage(User user, String message) {
        this.user = user;
        fields = new ArrayList<Field>();
        fields.add(new Field(":", message, false));
        this.message = message;
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
    public java.util.List<Field> getFields() {
        return fields;
    }
}
