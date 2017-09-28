package nl.UnderKoen.UnderBot.threads;

import net.dv8tion.jda.core.entities.TextChannel;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;
import nl.UnderKoen.UnderBot.utils.Messages.TwitterMessage;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Under_Koen on 22-08-17.
 */
public class Twittercheck {
    public static TextChannel channel;
    private static TwitterStream twitterStream;
    public static String user;
    public static boolean check;

    //consumerKey, consumerSecret, token, tokenSecret
    public static void start(String consumerKey, String consumerSecret, String token, String tokenSecret) {
        if (!check) {
            stop();
            return;
        }
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret);

        TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(cb.build());
        twitterStream = twitterStreamFactory.getInstance(new AccessToken(token, tokenSecret));
        twitterStream.onStatus(status -> {
            if (status.getUser().getScreenName().equalsIgnoreCase(user)) {
                if (status.getInReplyToScreenName() != null) return;
                new TwitterMessage().setStatus(status).sendMessage(channel);
            }
        });
        twitterStream.user();
    }

    private static void stop() {
        twitterStream.shutdown();
    }
}