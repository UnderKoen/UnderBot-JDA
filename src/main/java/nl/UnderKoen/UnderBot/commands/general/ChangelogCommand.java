package nl.UnderKoen.UnderBot.commands.general;

import com.google.gson.*;
import nl.UnderKoen.UnderBot.Main;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Created by Under_Koen on 23-04-17.
 */
public class ChangelogCommand implements Command {
    private String command = "changelog";
    private String usage = "/changelog (version)";
    private String description = "Returns the changelog of a version.";

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
    public void setup() throws Exception {
        File file = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        if (!new File(file.getParent() + "/Changelogs").exists()) new File(file.getParent() + "/Changelogs").mkdir();
        file = new File(file.getParent() + "/Changelogs/Changelog_" + Main.version + ".json");
        //if (file.exists()) return;
        file.createNewFile();
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(getCurrentChangelog());
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCurrentChangelog() {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("Changelog.json");

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();

    }

    private String getChangelog(String version) throws Exception {
        StringBuilder result = new StringBuilder("");

        File file = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        file = new File(file.getParent() + "/Changelogs/Changelog_" + version + ".json");
        if (!file.exists()) throw new Exception();

        Files.lines(file.toPath()).forEach(s -> {
            result.append(s);
        });

        return result.toString();
    }

    @Override
    public void run(CommandContext context) {
        String version = Main.version;
        if (context.getArgs().length > 0) {
            version = context.getArgs()[0];
        }
        String changelog = "";
        try {
            changelog = getChangelog(version);
        } catch (Exception e) {
            new ErrorMessage(context.getUser(), "The changelog for " + version + " does not exist")
                    .sendMessage(context.getChannel());
        }
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(changelog).getAsJsonObject();
        String added = "";
        for (JsonElement obj: o.get("ADDED").getAsJsonArray()) {
            added = added + "- " + obj.getAsString() + "\n";
        }
        String removed = "";
        for (JsonElement obj: o.get("REMOVED").getAsJsonArray()) {
            removed = removed + "- " + obj.getAsString() + "\n";
        }
        String fixed = "";
        for (JsonElement obj: o.get("FIXED").getAsJsonArray()) {
            fixed = fixed + "- " + obj.getAsString() + "\n";
        }
        String todo = "";
        for (JsonElement obj: o.get("TODO").getAsJsonArray()) {
            todo = todo + "- " + obj.getAsString() + "\n";
        }
        TextMessage message = new TextMessage().addMention(context.getUser()).addText("The changelog of version " + version);
        message.addField("Added", added, false);
        message.addField("Removed", removed, false);
        message.addField("Fixed", fixed, false);
        message.addField("Todo", todo, false);
        message.sendMessage(context.getChannel());
    }
}
