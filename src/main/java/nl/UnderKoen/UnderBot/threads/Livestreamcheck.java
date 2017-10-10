package nl.underkoen.underbot.threads;

import net.dv8tion.jda.core.entities.TextChannel;
import nl.underkoen.underbot.utils.YoutubeUtil;

/**
 * Created by Under_Koen on 09-05-17.
 */
public class Livestreamcheck extends Thread {
    public static boolean check = false;
    public static TextChannel channel;

    public static String channelId = "UC8cgXXpepeB2CWfxdy7uGVg";

    public String Current = "";
    public String Last = "";

    @Override
    public void run() {
        try {
            while (check) {
                Current = YoutubeUtil.getLivestream(channelId);
                if (!Current.contentEquals(Last)) {
                    if (!Current.isEmpty()) channel.sendMessage("@everyone Tim is live op https://www.youtube.com/makertim/live").complete();
                }
                Last = Current;
                this.sleep(5 * 60 * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}