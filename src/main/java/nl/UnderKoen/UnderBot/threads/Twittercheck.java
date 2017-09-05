package nl.UnderKoen.UnderBot.threads;

import net.dv8tion.jda.core.entities.TextChannel;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Under_Koen on 22-08-17.
 */
public class Twittercheck {
    /*public static TextChannel channel;
    private static TwitterStream twitterStream;

    //consumerKey, consumerSecret, token, tokenSecret
    public void start() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(args[0])
                .setOAuthConsumerSecret(args[1]);

        TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(cb.build());
        twitterStream = twitterStreamFactory.getInstance(new AccessToken(args[2], args[3]));
        twitterStream.onStatus(status -> {
            if (status.getUser().getScreenName().equalsIgnoreCase("makertim")) {
                System.out.println(status.getText());
            }
        });
        twitterStream.user();
    }

    public void stop() {
        twitterStream.shutdown();
    }*/
}