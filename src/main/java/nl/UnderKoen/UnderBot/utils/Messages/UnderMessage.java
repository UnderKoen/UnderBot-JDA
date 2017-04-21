package nl.UnderKoen.UnderBot.utils.Messages;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.*;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.MessageEmbedImpl;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Under_Koen on 19-04-17.
 */
public interface UnderMessage {

    public default Color getColor() {
        return null;
    }

    public default User getAuthor() {
        return null;
    }

    public default List<Field> getFields() {
        return new ArrayList<Field>();
    }

    public default Footer getFooter() {
        return null;
    }

    public default String getDescription() {
        return null;
    }

    public default void sendMessage(TextChannel channel) {

        EmbedBuilder msg = new EmbedBuilder();

        //MessageEmbedImpl msg = new MessageEmbedImpl();

        Color color = getColor();
        if (color != null) {
            msg.setColor(color);
        }

        User author = getAuthor();
        if (author != null) {
            msg.setAuthor(author.getName(), null, author.getAvatarUrl());
        }

        List<Field> fields = getFields();
        if (fields != null) {
            for (Field field : fields) {
                msg.addField(field);
            }
        }

        Footer footer = getFooter();
        if (footer != null) {
            msg.setFooter(footer.getText(), footer.getIconUrl());
        }

        String desc = getDescription();
        if (desc != null) {
            msg.setDescription(desc);
        }

        channel.sendMessage(msg.build()).complete();
    }
}
