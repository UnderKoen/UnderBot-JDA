package nl.UnderKoen.UnderBot.utils.Messages;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import nl.UnderKoen.UnderBot.Main;
import nl.UnderKoen.UnderBot.utils.ColorUtil;

import java.awt.*;
import java.awt.List;
import java.util.*;

/**
 * Created by Under_Koen on 02-05-17.
 */
public class EmoteMessage implements UnderMessage {
    private Color color = ColorUtil.hexToColor("#e0f444");

    private User user;

    private String text;

    public EmoteMessage (String text) {
        this.text = text;
    }

    public EmoteMessage addMention(User user) {
        this.user = user;
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
    public void sendMessage(TextChannel channel) {
        channel.sendMessage(text).complete();

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

        channel.sendMessage(msg.build()).complete();
    }
}