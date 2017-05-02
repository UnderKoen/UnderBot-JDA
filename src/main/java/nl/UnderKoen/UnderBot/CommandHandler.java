package nl.UnderKoen.UnderBot;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.impl.CommandContextImpl;
import nl.UnderKoen.UnderBot.exceptions.AlreadyInitializedException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Under_Koen on 19-04-17.
 */
public class CommandHandler extends ListenerAdapter {
    private HashMap<String, Command> commands;
    private String prefix;

    public void initializeCommand(Command command) {
        try {
            command.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String commandName = command.getCommand();
        if (commands.containsKey(commandName)) throw new AlreadyInitializedException();
        commands.put(commandName, command);
    }

    public CommandHandler(String prefix) {
        commands = new HashMap<String, Command>();
        this.prefix = prefix;
    }

    public ArrayList<Command> getAllCommands() {
        return new ArrayList<Command>(commands.values());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String messageContent = event.getMessage().getContent();
        if (!messageContent.startsWith(prefix)) return;

        CommandContextImpl context = new CommandContextImpl();

        context.setPrefix(prefix);

        String commandName = messageContent.split(" ")[0].trim().replaceFirst(prefix, "");
        context.setCommand(commandName);

        if (!commands.containsKey(commandName)) return;

        String[] argsTest = messageContent.split(" ");
        ArrayList<String> args = new ArrayList<String>();
        for (String arg : argsTest) {
            if (argsTest[0] == arg) continue;
            args.add(arg);
        }
        context.setArgs(args.toArray(new String[0]));

        User user = event.getAuthor();
        context.setUser(user);

        TextChannel channel = event.getTextChannel();
        context.setChannel(channel);

        Guild guild = event.getGuild();
        context.setGuild(guild);

        Member member = event.getMember();
        context.setMember(member);

        Message message = event.getMessage();
        context.setMessage(message);

        Command command = commands.get(commandName);

        try {
            command.run(context);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            message.delete().complete();
        } catch (Exception ex) {
        }
    }
}
