package nl.underkoen.underbot.utils.Messages;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.entities.MessageEmbed.*;
import nl.underkoen.underbot.Main;
import nl.underkoen.underbot.commands.Command;
import nl.underkoen.underbot.commands.MainCommand;
import nl.underkoen.underbot.utils.ColorUtil;
import nl.underkoen.underbot.utils.RoleUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Under_Koen on 21-04-17.
 */
public class HelpMessage implements UnderMessage {

    private Color color = ColorUtil.hexToColor("#2a6886");

    private Member user;

    private String message;

    private boolean subcommands;

    private List<Command> commands;

    public HelpMessage setMention(Member user) {
        this.user = user;
        return this;
    }

    public HelpMessage addText(String text) {
        if (message == null) {
            message = "";
        } else {
            message = message + "\n";
        }
        message = message + text;
        return this;
    }

    public HelpMessage addCommand(Command command) {
        if (this.commands == null) this.commands = new ArrayList<>();
        this.commands.add(command);
        return this;
    }

    public HelpMessage addCommands(List<Command> commands) {
        if (this.commands == null) this.commands = new ArrayList<>();
        this.commands.addAll(commands);
        return this;
    }

    public HelpMessage showSubcommands(boolean show) {
        subcommands = show;
        return this;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Member getAuthor() {
        return user;
    }

    @Override
    public String getDescription() {
        return message;
    }

    @Override
    public void sendMessage(TextChannel channel) {

        EmbedBuilder msg = new EmbedBuilder();

        Color color = getColor();
        if (color != null) {
            msg.setColor(color);
        }

        Member author = getAuthor();
        if (author != null) {
            msg.setFooter(author.getEffectiveName(), author.getUser().getAvatarUrl());
        }

        String desc = getDescription();
        if (desc != null) {
            msg.setDescription(desc);
        }

        int role = RoleUtil.getHighestRole(channel.getGuild()).getPosition();
        if (author != null) {
            role = RoleUtil.getHighestRole(author).getPosition();
        }

        List<Role> roles = new ArrayList<>(channel.getGuild().getRoles());
        Collections.reverse(roles);
        int finalRole = role;
        List<Command> mainCommands = new ArrayList<>();
        roles.forEach(role1 -> {
            if (role1.getPosition() > finalRole) return;
            List<Command> roleCommands = new ArrayList<>(commands);
            roleCommands.removeIf(command -> role1.getPosition() != command.getMinimumRole());
            StringBuilder builder = new StringBuilder();
            roleCommands.forEach(command -> {
                if (!(command instanceof MainCommand && subcommands)) {
                    builder.append("**" + command.getUsage() + "** -> " + command.getDescription() + "\n");
                } else {
                    mainCommands.add(command);
                }
            });
            if (!builder.toString().isEmpty()) msg.addField(new Field(role1.getName(), builder.toString(), false));
        });
        mainCommands.forEach(command -> {
            StringBuilder builder = new StringBuilder();
            builder.append("**" + command.getUsage() + "** -> " + command.getDescription() + "\n");
            if (command instanceof MainCommand && subcommands) {
                MainCommand mainCommand = (MainCommand) command;
                mainCommand.getSubcommands().forEach(subcommand -> {
                    builder.append("    - **" + subcommand.getUsage().replace(Main.handler.getPrefix(),"") + "** -> " + subcommand.getDescription() + "\n");
                });
            }
            if (!builder.toString().isEmpty()) msg.addField(new Field(command.getCommand(), builder.toString(), false));
        });

        Message ms = channel.sendMessage(msg.build()).complete();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        ms.delete().complete();
                    }
                },
                TimeUnit.MINUTES.toMillis(5)
        );
    }
}