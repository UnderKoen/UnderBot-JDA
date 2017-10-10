package nl.underkoen.underbot.entities;
import net.dv8tion.jda.core.entities.*;

/**
 * Created by Under_Koen on 19-04-17.
 */
public interface CommandContext {
    String getPrefix();

    String getCommand();

    String[] getArgs();

    String[] getRawArgs();

    User getUser();

    TextChannel getChannel();

    Guild getGuild();

    Member getMember();

    Message getMessage();
}
