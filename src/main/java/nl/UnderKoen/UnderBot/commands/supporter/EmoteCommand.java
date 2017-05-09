package nl.UnderKoen.UnderBot.commands.supporter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.UnderKoen.UnderBot.Main;
import nl.UnderKoen.UnderBot.commands.Command;
import nl.UnderKoen.UnderBot.entities.CommandContext;
import nl.UnderKoen.UnderBot.utils.Messages.ErrorMessage;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Under_Koen on 02-05-17.
 */
public class EmoteCommand implements Command {
    private String command = "emote";
    private String usage = "/emote [text...]";
    private String description = "Sends your message in emote style";
    private int minimumRole = 1;

    @Override
    public int getMinimumRole() {
        return minimumRole;
    }

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
        file = new File(file.getParent() + "/Emote.json");
        if (file.exists()) return;
        file.createNewFile();
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(getEmotesResource());
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getEmotesResource() {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("Emote.json");

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

    private String getEmotes() {

        StringBuilder result = new StringBuilder("");

        try {
            File file = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            file = new File(file.getParent() + "/Emote.json");

            try (Scanner scanner = new Scanner(file)) {

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    result.append(line).append("\n");
                }

                scanner.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {}

        return result.toString();
    }

    @Override
    public void run(CommandContext context) throws Exception {
        if (context.getArgs().length == 0) {
            new ErrorMessage(context.getUser(), "No text to change to emote text")
                    .sendMessage(context.getChannel());
            return;
        }
        String args = "";
        for (String arg: context.getArgs()) {
            args = args + arg + " ";
        }
        args = args.trim().toLowerCase();
        String text = "";
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(getEmotes()).getAsJsonObject();
        for (char Char: args.toCharArray()) {
            if (o.has(Char + "")) {
                text = text + o.get(Char + "").getAsString() + " ";
            } else {
                text = text + o.get("none").getAsString() + " ";
            }
        }
        new TextMessage().addText(text).setMention(context.getUser()).sendMessage(context.getChannel());
    }
}