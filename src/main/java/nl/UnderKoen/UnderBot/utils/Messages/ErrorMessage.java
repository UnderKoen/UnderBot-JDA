package nl.UnderKoen.UnderBot.utils.Messages;

import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import net.dv8tion.jda.core.entities.User;
import nl.UnderKoen.UnderBot.utils.Messages.UnderMessage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Under_Koen on 19-04-17.
 */
public class ErrorMessage implements UnderMessage {

    private Color color = Color.RED;

    private User user;

    private List<Field> fields;

    public ErrorMessage(User user, String error) {
        this.user = user;
        fields = new ArrayList<Field>();
        fields.add(new Field("Error:", error, true));
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
    public List<Field> getFields() {
        return fields;
    }
}
