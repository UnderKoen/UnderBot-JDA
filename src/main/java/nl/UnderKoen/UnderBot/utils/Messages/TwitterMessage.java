package nl.underkoen.underbot.utils.Messages;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import nl.underkoen.underbot.utils.ColorUtil;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Under_Koen on 06/09/2017.
 */
public class TwitterMessage implements UnderMessage {
    private Color color = ColorUtil.hexToColor("#2a6886");

    private Status status;

    public TwitterMessage setStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public void sendMessage(TextChannel channel) {

        //System.out.println();

        EmbedBuilder msg = new EmbedBuilder();

        if (color != null) {
            msg.setColor(color);
        }
        msg.setAuthor(status.getUser().getName() + " (@" + status.getUser().getScreenName() + ")",
                "https://twitter.com/" + status.getUser().getScreenName()
                        + "/status/" + status.getId(),
                status.getUser().getOriginalProfileImageURL());

        String text = status.getText();

        for (URLEntity url : status.getURLEntities()) {
            msg.setImage(url.getExpandedURL());
            text = text.replace(url.getURL(), url.getExpandedURL());
        }

        if (status.isRetweet()) {
            for (URLEntity url : status.getRetweetedStatus().getURLEntities()) {
                msg.setImage(url.getExpandedURL());
                text = text.replace(url.getURL(), url.getExpandedURL());
            }
        }

        for (MediaEntity url : status.getMediaEntities()) {
            msg.setImage(url.getMediaURLHttps());
            text = text.replace(url.getURL(), url.getExpandedURL());
        }

        if (status.isRetweet()) {
            for (MediaEntity url : status.getRetweetedStatus().getMediaEntities()) {
                msg.setImage(url.getMediaURLHttps());
                text = text.replace(url.getURL(), url.getExpandedURL());
            }
        }

        msg.setDescription(text);

        msg.setFooter("Twitter", "https://images-ext-1.discordapp.net/external/bXJWV2Y_F3XSra_kEqIYXAAsI3m1meckfLhYuWzxIfI/https/abs.twimg.com/icons/apple-touch-icon-192x192.png");

        Message ms = channel.sendMessage(msg.build()).complete();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        ms.delete().complete();
                    }
                },
                TimeUnit.DAYS.toMillis(1)
        );
    }
}
