package nl.UnderKoen.UnderBot.commands.moderator;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import nl.UnderKoen.UnderBot.Roles;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Under_Koen on 04-05-17.
 */
public class TimeoutCommand implements Command {
    private String command = "timeout";
    private String usage = "/timeout [User] [Lenght] (reason...)";
    private String description = "Time out a user so he can't talk. \nLenght like 1d2u10m3s";
    private int minimumRole = Roles.MOD.role;

    @Override
    public int getMinimumRole() {
        return minimumRole;
    }

    private static HashMap<User, Timestamp> timeouts = new HashMap<User, Timestamp>();

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setup() throws Exception {}

    @Override
    public void run(CommandContext context) {
        if (context.getRawArgs().length == 0 || context.getRawArgs().length < 2) {
            new ErrorMessage(context.getUser(), ("This command needs " +  ((context.getRawArgs().length == 0)? "" : "more") +" arguments to work"))
                    .sendMessage(context.getChannel());
            return;
        }
        TextMessage message = new TextMessage();
        message.setMention(context.getUser());
        Pattern pattern = Pattern.compile("<@!?(\\d+)>");
        Matcher matcher = pattern.matcher(context.getRawArgs()[0]);
        matcher.find();
        Member member = null;
        try {
            member = context.getGuild().getMemberById(matcher.group(1));
        } catch (Exception e) {
            new ErrorMessage(context.getUser(), context.getRawArgs()[0] + " is not a valid user.")
                    .sendMessage(context.getChannel());
            return;
        }
        pattern = Pattern.compile("(\\d+)([smhdwMy])");
        matcher = pattern.matcher(context.getRawArgs()[1]);
        LocalDateTime lenght = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
        while (matcher.find()) {
            TemporalUnit unit = null;
            switch (matcher.group(2)) {
                case "s":
                    unit = ChronoUnit.SECONDS;
                    break;
                case "m":
                    unit = ChronoUnit.MINUTES;
                    break;
                case "h":
                    unit = ChronoUnit.HOURS;
                    break;
                case "d":
                    unit = ChronoUnit.DAYS;
                    break;
                case "w":
                    unit = ChronoUnit.WEEKS;
                    break;
                case "M":
                    unit = ChronoUnit.MONTHS;
                    break;
                case "y":
                    unit = ChronoUnit.YEARS;
                    break;
            }
            lenght = lenght.plus(Integer.parseInt(matcher.group(1)), unit);
        }
        if (!matcher.replaceAll("").isEmpty()) {
            new ErrorMessage(context.getUser(), context.getRawArgs()[1] + " is not a valid lenght.")
                    .sendMessage(context.getChannel());
            return;
        }
        timeouts.put(member.getUser(), Timestamp.valueOf(lenght));
        TextMessage msg = new TextMessage().addText("Timeout information")
                .setMention(context.getUser())
                .addField("User", member.getAsMention(), false)
                .addField("Until", Timestamp.valueOf(lenght).toGMTString(), false);
        if (context.getRawArgs().length >= 3) {
            StringBuilder str = new StringBuilder();
            int i = 1;
            for (String arg: context.getRawArgs()) {
                if (i <= 2) {
                    i++;
                    continue;
                }
                str.append(arg).append(" ");
                i++;
            }
            msg.addField("Reason", str.toString(), false);
        }
        msg.sendMessage(context.getChannel());
    }

    public static boolean isTimeouted(User user) {
        if (!timeouts.containsKey(user)) return false;
        return timeouts.get(user).after(new Timestamp(System.currentTimeMillis()));
    }
}
