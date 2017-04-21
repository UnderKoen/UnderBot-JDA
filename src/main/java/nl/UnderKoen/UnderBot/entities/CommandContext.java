package nl.UnderKoen.UnderBot.entities;
import net.dv8tion.jda.core.entities.*;

/**
 * Created by Under_Koen on 19-04-17.
 */
public interface CommandContext {
    public String getPrefix();

    public String getCommand();

    public String[] getArgs();

    public User getUser();

    public TextChannel getChannel();

    public Guild getGuild();

    public Member getMember();

    public Message getMessage();
}
