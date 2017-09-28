package nl.UnderKoen.UnderBot.utils.Messages;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import nl.UnderKoen.UnderBot.utils.ColorUtil;
import twitter4j.Status;

import java.awt.*;

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

        EmbedBuilder msg = new EmbedBuilder();

        Color color = getColor();
        if (color != null) {
            msg.setColor(color);
        }
        msg.setAuthor(status.getUser().getName() + " (@" + status.getUser().getScreenName() + ")",
                "https://twitter.com/" + status.getUser().getScreenName()
                        + "/status/" + status.getId(),
                status.getUser().getOriginalProfileImageURL());

        msg.setDescription(status.getText());

        msg.setFooter("Twitter", "https://images-ext-1.discordapp.net/external/bXJWV2Y_F3XSra_kEqIYXAAsI3m1meckfLhYuWzxIfI/https/abs.twimg.com/icons/apple-touch-icon-192x192.png");

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
