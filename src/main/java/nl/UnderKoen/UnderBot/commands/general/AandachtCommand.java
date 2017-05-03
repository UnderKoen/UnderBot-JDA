package nl.UnderKoen.UnderBot.commands.general;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.core.entities.User;
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
 * Created by Under_Koen on 03-05-17.
 */
public class AandachtCommand implements Command {
    private String command = "aandacht";
    private String usage = "/aandacht";
    private String description = "Sends a line from AANDACHT - Kud";

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
        file = new File(file.getParent() + "/Aandacht.txt");
        if (!file.exists()) {
            file.createNewFile();
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(getAandachtResource());
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        aandacht = getAandacht();
    }

    private String getAandachtResource() {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("Aandacht.txt");

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

    private ArrayList<String> getAandacht() {

        ArrayList<String> result = new ArrayList<String>();

        try {
            File file = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            file = new File(file.getParent() + "/Aandacht.txt");

            try (Scanner scanner = new Scanner(file)) {

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    result.add(line);
                }

                scanner.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {}

        return result;
    }

    private ArrayList<String> aandacht;
    private int current = 0;
    private User last;

    @Override
    public void run(CommandContext context) throws Exception {
        if (context.getUser() != last) {
            new TextMessage().addText(aandacht.get(current)).addMention(context.getUser()).sendMessage(context.getChannel());
            last = context.getUser();
            if (current == aandacht.size()-1) {
                current = 0;
            } else {
                current = current+1;
            }
        }
    }
}
