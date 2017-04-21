package nl.UnderKoen.UnderBot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import nl.UnderKoen.UnderBot.commands.Command;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.net.URL;
import java.util.Set;

/**
 * Created by Under_Koen on 18-04-17.
 */
public class Main {

    public static JDA jda;
    public static CommandHandler handler;

    public static String version = "0.0.1";

    public static void main(String[] args) {
        handler = new CommandHandler("/");
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

        initializeAllCommands("nl.UnderKoen.UnderBot.commands", handler);
    }

    //ugly code so i don't need to initialize every command by hand
    public static void initializeAllCommands(String pckgname, CommandHandler handler) {
        String name = new String(pckgname);
        if (!name.startsWith("/")) {
            name = "/" + name;
        }
        name = name.replace('.', '/');
        URL url = Main.class.getResource(name);
        File directory = new File(url.getFile());
        if (directory.exists()) {
            String[] files = directory.list();
            for (int i = 0; i < files.length; i++) {
                String className = files[i];
                if (!files[i].contains(".")) {
                    initializeAllCommands(pckgname + "." + className, handler);
                    continue;
                }
                if (files[i].endsWith(".class")) {
                    String classname = className.substring(0,files[i].length()-6);
                    try {
                        // Try to create an instance of the object
                        Object o = Class.forName(pckgname+"."+classname).newInstance();
                        if (o instanceof Command) {
                            Command command = (Command) o;
                            handler.initializeCommand(command);
                        }
                    } catch (ClassNotFoundException cnfex) {
                        System.err.println(cnfex);
                    } catch (InstantiationException iex) {
                        // We try to instantiate an interface
                        // or an object that does not have a
                        // default constructor
                    } catch (IllegalAccessException iaex) {
                        // The class is not public
                    }
                }
            }
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
