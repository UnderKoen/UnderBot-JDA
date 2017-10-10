package nl.underkoen.underbot.utils;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

/**
 * Created by Under_Koen on 06-05-17.
 */
public class RoleUtil {
    public static Role getHighestRole(Member user) {
        Role highest = user.getGuild().getPublicRole();
        for (Role role: user.getRoles()) {
            if (highest.getPosition() >= role.getPosition()) continue;
            highest = role;
        }
        return highest;
    }

    public static Role getHighestRole(Guild guild) {
        Role highest = guild.getPublicRole();
        for (Role role: guild.getRoles()) {
            if (highest.getPosition() >= role.getPosition()) continue;
            highest = role;
        }
        return highest;
    }

    public static Role getRole(Guild guild, int position) {
        Role role = null;
        for (Role rol: guild.getRoles()) {
            if (position != rol.getPosition()) continue;
            role = rol;
        }
        if (role != null) return role;
        int lowest = 0;
        for (Role rol: guild.getRoles()) {
            if (position < rol.getPosition()) continue;
            if (!(lowest < rol.getPosition() || lowest == 0)) continue;
            lowest = rol.getPosition();
            role = rol;
        }
        return role;
    }
}
