package nl.UnderKoen.UnderBot.utils.Messages;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
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

    default Color getColor() {
        return null;
    }

    default User getAuthor() {
        return null;
    }

    default List<Field> getFields() {
        return new ArrayList<Field>();
    }

    default String getDescription() {
        return null;
    }

    default void sendMessage(TextChannel channel) {

        EmbedBuilder msg = new EmbedBuilder();

        //MessageEmbedImpl msg = new MessageEmbedImpl();

        Color color = getColor();
        if (color != null) {
            msg.setColor(color);
        }

        User author = getAuthor();
        if (author != null) {
            msg.setFooter(author.getName(), author.getAvatarUrl());
        }

        List<Field> fields = getFields();
        if (fields != null) {
            for (Field field : fields) {
                msg.addField(field);
            }
        }

        String desc = getDescription();
        if (desc != null) {
            msg.setDescription(desc);
        }

        Message ms = channel.sendMessage(msg.build()).complete();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        ms.delete().complete();
                    }
                },
                1000 * 60 * 5
        );
    }
}
