package nl.UnderKoen.UnderBot.utils.messages;

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

    public default List<Field> getFields(){
        return new ArrayList<Field>();
    }

    public default Footer getFooter() {
        return null;
    }

    public default String getDescription() {
        return null;
    }

    public default String getUrl() {
        return null;
    }

    public default void sendMessage(TextChannel channel) {
        MessageEmbedImpl msg = new MessageEmbedImpl();

        Color color = getColor();
        if (color != null) {
            msg.setColor(color);
        }

        User author = getAuthor();
        if (author != null) {
            msg.setAuthor(new MessageEmbed.AuthorInfo(author.getName(), "", author.getAvatarUrl(), ""));
        }

        List<Field> fields = getFields();
        if (fields != null) {
            msg.setFields(fields);
        } else {
            msg.setFields(new ArrayList<Field>());
        }

        Footer footer = getFooter();
        if (footer != null) {
            msg.setFooter(footer);
        }

        String desc = getDescription();
        if (desc != null) {
            msg.setDescription(desc);
        }

        String url = getUrl();
        if (url != null) {
            msg.setUrl(url);
        }

        channel.sendMessage(msg).complete();
    }
}
