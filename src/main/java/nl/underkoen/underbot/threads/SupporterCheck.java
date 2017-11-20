package nl.underkoen.underbot.threads;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.managers.GuildController;
import nl.underkoen.underbot.Main;
import nl.underkoen.underbot.Roles;
import nl.underkoen.underbot.commands.hitbox.LinkDiscord;
import nl.underkoen.underbot.hitbox.UserInfo;
import nl.underkoen.underbot.utils.RoleUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by Under_Koen on 05/11/2017.
 */
public class SupporterCheck extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                JsonObject json = new JsonParser().parse(LinkDiscord.getFile()).getAsJsonObject();
                for (JsonElement jsonE : json.getAsJsonArray("linked")) {
                    JsonObject linked = jsonE.getAsJsonObject();
                    UserInfo userInfo = Main.hitboxUtil.getUserInfo(linked.get("hitboxName").getAsString());
                    String discordName = linked.get("discordName").getAsString();
                    String discordId = linked.get("discordId").getAsString();
                    String hitboxName = userInfo.getName();
                    boolean isSubscriberbefore = linked.get("isSubscriber").getAsBoolean();
                    boolean isSubscriber = userInfo.isSubscriber();
                    LinkDiscord.addUser(discordName, discordId, hitboxName, isSubscriber);
                    if (isSubscriber) {
                        Guild guild = Main.jda.getGuilds().get(0);
                        User user = getUser(discordName, discordId);
                        if (user != null) {
                            Member member = guild.getMember(user);
                            new GuildController(guild).addSingleRoleToMember(member, RoleUtil.getRole(guild, Roles.SUPER_SUPPORTER_HITBOX.role)).complete();
                        }
                    } else if (isSubscriber != isSubscriberbefore) {
                        Guild guild = Main.jda.getGuilds().get(0);
                        User user = getUser(discordName, discordId);
                        if (user != null) {
                            Member member = guild.getMember(user);
                            new GuildController(guild).removeSingleRoleFromMember(member, RoleUtil.getRole(guild, Roles.SUPER_SUPPORTER_HITBOX.role)).complete();
                        }
                    }
                }
                sleep(TimeUnit.MINUTES.toMillis(30));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private User getUser(String name, String id) {
        User hit = null;
        for (User user : Main.jda.getUsersByName(name, true)) {
            if (user.getDiscriminator().equalsIgnoreCase(id)) {
                hit = user;
                break;
            }
        }
        return hit;
    }
}
