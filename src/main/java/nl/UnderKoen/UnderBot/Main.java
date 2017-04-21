package nl.UnderKoen.UnderBot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import nl.UnderKoen.UnderBot.commands.TestCommand;

import javax.security.auth.login.LoginException;

/**
 * Created by Under_Koen on 18-04-17.
 */
public class Main {

    public static JDA jda;
    public static CommandHandler handler;

    public static void main(String[] args) {
        handler = new CommandHandler("/");
        handler.initializeCommand(new TestCommand());
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(args[0])
                    .addEventListener(handler)
                    .buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public void onEvent(Event event) {
        if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent Event = (MessageReceivedEvent) event;
            if (Event.getAuthor().isBot()) return;
            //Color[] colors = new Color[]{Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY,
            //        Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK,
            //        Color.RED, Color.WHITE, Color.YELLOW
            //};
            Color color = Color.CYAN;
            //for (Color color : colors) {
            ArrayList<MessageEmbed.Field> fields = new ArrayList<MessageEmbed.Field>();
            fields.add(new MessageEmbed.Field("Message:", Event.getMessage().getContent(), true));
            MessageEmbedImpl msg = new MessageEmbedImpl().setFields(fields);
            msg.setColor(color);
            msg.setFooter(new MessageEmbed.Footer(jda.getSelfUser().getName(), jda.getSelfUser().getAvatarUrl(), ""));
            msg.setAuthor(new MessageEmbed.AuthorInfo(Event.getAuthor().getName(), "", Event.getAuthor().getAvatarUrl(), ""));
            Event.getTextChannel().sendMessage(msg).complete();
            //}
        }
    }*/


}
