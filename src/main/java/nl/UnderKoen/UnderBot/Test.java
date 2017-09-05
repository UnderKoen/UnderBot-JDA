package nl.UnderKoen.UnderBot;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class Test {
    private static TwitterStream twitterStream;

    //consumerKey, consumerSecret, token, tokenSecret
    public static void main(String args[]) throws Exception {
        /*TwitterStreamFactory factory = new TwitterStreamFactory();
        AccessToken accessToken = new AccessToken(args[2], args[3]);
        TwitterStream twitter = factory.getInstance();
        twitter.setOAuthConsumer(args[0], args[1]);
        twitter.setOAuthAccessToken(accessToken);*/

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
}
