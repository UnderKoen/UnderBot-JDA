package nl.UnderKoen.UnderBot;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import nl.UnderKoen.UnderBot.commands.Command;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Under_Koen on 18-04-17.
 */
public class Main {

    public static JDA jda;
    public static CommandHandler handler;

    public static String version = "0.0.1";

    public static void main(String[] args) {handler = new CommandHandler("/");
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

    public static void initializeAllCommands(String pckgname, CommandHandler handler) {
        try {
            ZipInputStream zip = new ZipInputStream(new FileInputStream(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                if (!entry.isDirectory() &&
                        entry.getName().endsWith(".class") &&
                        entry.getName().replace('/', '.').startsWith(pckgname)) {
                    String className = entry.getName().replace('/', '.');
                    if (className.endsWith(".class")) {
                        String classname = className.substring(0, className.length() - 6);
                        try {
                            // Try to create an instance of the object
                            Object o = Class.forName(classname).newInstance();
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
        } catch (Exception e) {
            initializeAllCommandsInDir(pckgname, handler);
        }
    }

    //ugly code so i don't need to initialize every command by hand
    private static void initializeAllCommandsInDir(String pckgname, CommandHandler handler) {
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
                    String classname = className.substring(0, files[i].length() - 6);
                    try {
                        // Try to create an instance of the object
                        Object o = Class.forName(pckgname + "." + classname).newInstance();
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


}
