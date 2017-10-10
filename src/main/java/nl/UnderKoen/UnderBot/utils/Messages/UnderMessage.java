package nl.underkoen.underbot.utils.Messages;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.entities.MessageEmbed.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Under_Koen on 19-04-17.
 */
public interface UnderMessage {

    default Color getColor() {
        return null;
    }

    default Member getAuthor() {
        return null;
    }

    default List<Field> getFields() {
        return new ArrayList<Field>();
    }

    default String getDescription() {
        return null;
    }

    default String getTitle() {
        return null;
    }

    default String getUrl() {
        return null;
    }

    default void sendMessage(TextChannel channel) {

        EmbedBuilder msg = new EmbedBuilder();

        Color color = getColor();
        if (color != null) {
            msg.setColor(color);
        }

        Member author = getAuthor();
        if (author != null) {
            msg.setFooter(author.getEffectiveName(), author.getUser().getAvatarUrl());
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

        String title = getTitle();
        String url = getUrl();
        if (title != null) {
            msg.setTitle(title, url);
        }

        Message ms = channel.sendMessage(msg.build()).complete();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        ms.delete().complete();
                    }
                },
                TimeUnit.MINUTES.toMillis(5)
        );
    }
}
