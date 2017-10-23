package nl.underkoen.underbot;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import nl.underkoen.underbot.commands.Command;
import nl.underkoen.underbot.commands.moderator.TimeoutCommand;
import nl.underkoen.underbot.entities.impl.CommandContextImpl;
import nl.underkoen.underbot.exceptions.AlreadyInitializedException;
import nl.underkoen.underbot.utils.Messages.ErrorMessage;
import nl.underkoen.underbot.utils.RoleUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Under_Koen on 19-04-17.
 */
public class CommandHandler extends ListenerAdapter {
    private HashMap<String, Command> commands;
    private HashMap<String, String> aliases;
    private String prefix;

    public void initializeCommand(Command command) {
        try {
            command.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String commandName = command.getCommand();
        if (commands.containsKey(commandName) || aliases.containsKey(commandName)) throw new AlreadyInitializedException();
        commands.put(commandName, command);
        String[] aliases = command.getAliases();
        for (String alias: aliases) {
            if (this.aliases.containsKey(alias) || commands.containsKey(alias)) continue;
            this.aliases.put(alias, commandName);
        }
    }

    public CommandHandler(String prefix) {
        commands = new HashMap<String, Command>();
        aliases = new HashMap<String, String>();
        this.prefix = prefix;
    }

    public ArrayList<Command> getAllCommands() {
        return new ArrayList<Command>(commands.values());
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String messageContent = event.getMessage().getContent();
        String messageRawContent = event.getMessage().getRawContent();

        if (!messageContent.startsWith(prefix)) return;

        CommandContextImpl context = new CommandContextImpl();

        context.setPrefix(prefix);

        String commandName = messageContent.split(" ")[0].trim().replaceFirst(prefix, "").toLowerCase();
        context.setCommand(commandName);

        if (!commands.containsKey(commandName) && !aliases.containsKey(commandName)) return;

        if (!commands.containsKey(commandName) && aliases.containsKey(commandName)) commandName = aliases.get(commandName);

        Command command = commands.get(commandName);

        if (RoleUtil.getHighestRole(event.getMember()).getPosition() < command.getMinimumRole()) {
            new ErrorMessage(event.getMember(), "The minimum role for /" + commandName + " is " +
                    RoleUtil.getRole(event.getGuild(), command.getMinimumRole()).getName()).sendMessage(event.getTextChannel());
            event.getMessage().delete().complete();
            return;
        }

        String[] argsTest = messageContent.split(" ");
        ArrayList<String> args = new ArrayList<String>();
        for (String arg : argsTest) {
            if (argsTest[0] == arg) continue;
            args.add(arg);
        }
        context.setArgs(args.toArray(new String[0]));

        String[] rawArgsTest = messageRawContent.split(" ");
        ArrayList<String> rawArgs = new ArrayList<String>();
        for (String rawArg : rawArgsTest) {
            if (rawArgsTest[0] == rawArg) continue;
            rawArgs.add(rawArg);
        }
        context.setRawArgs(rawArgs.toArray(new String[0]));

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

        try {
            command.run(context);
        } catch (Exception ex) {
            new ErrorMessage(context.getMember(), "A error occured").sendMessage(context.getChannel());
            ex.printStackTrace();
        }

        try {
            message.delete().complete();
        } catch (Exception ex) {
        }
    }
}
