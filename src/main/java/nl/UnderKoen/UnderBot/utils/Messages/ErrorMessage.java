package nl.underkoen.underbot.utils.Messages;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Under_Koen on 19-04-17.
 */
public class ErrorMessage implements UnderMessage {
    private Color color = Color.RED;

    private Member user;

    private List<Field> fields;

    public ErrorMessage(Member user, String error) {
        this.user = user;
        fields = new ArrayList<Field>();
        fields.add(new Field("Error:", error, true));
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Member getAuthor() {
        return user;
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }
}
