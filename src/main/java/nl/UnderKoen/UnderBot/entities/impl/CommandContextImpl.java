package nl.underkoen.underbot.entities.impl;

import net.dv8tion.jda.core.entities.*;
import nl.underkoen.underbot.entities.CommandContext;

/**
 * Created by Under_Koen on 19-04-17.
 */
public class CommandContextImpl implements CommandContext {

    private String prefix;
    private String command;
    private String[] args;
    private String[] rawArgs;
    private User user;
    private TextChannel channel;
    private Guild guild;
    private Member member;
    private Message message;

    @Override
    public String getPrefix() {
        return prefix;
    }

    public CommandContextImpl setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public String getCommand() {
        return command;
    }

    public CommandContextImpl setCommand(String command) {
        this.command = command;
        return this;
    }

    @Override
    public String[] getArgs() {
        return args;
    }

    public CommandContextImpl setArgs(String[] args) {
        this.args = args;
        return this;
    }

    @Override
    public String[] getRawArgs() {
        return rawArgs;
    }

    public CommandContextImpl setRawArgs(String[] rawArgs) {
        this.rawArgs = rawArgs;
        return this;
    }

    @Override
    public User getUser() {
        return user;
    }

    public CommandContextImpl setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public TextChannel getChannel() {
        return channel;
    }

    public CommandContextImpl setChannel(TextChannel channel) {
        this.channel = channel;
        return this;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public CommandContextImpl setGuild(Guild guild) {
        this.guild = guild;
        return this;
    }

    @Override
    public Member getMember() {
        return member;
    }

    public CommandContextImpl setMember(Member member) {
        this.member = member;
        return this;
    }

    @Override
    public Message getMessage() {
        return message;
    }

    public CommandContextImpl setMessage(Message message) {
        this.message = message;
        return this;
    }
}
