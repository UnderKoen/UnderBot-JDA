package nl.underkoen.underbot;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.impl.RoleImpl;
import net.dv8tion.jda.core.managers.GuildController;
import nl.underkoen.underbot.commands.Command;
import nl.underkoen.underbot.commands.hitbox.LinkDiscord;
import nl.underkoen.underbot.hitbox.ChatMsg;
import nl.underkoen.underbot.hitbox.HitboxUtil;
import nl.underkoen.underbot.hitbox.UserInfo;
import nl.underkoen.underbot.minesweeper.commands.MinesweeperCommand;
import nl.underkoen.underbot.music.commands.MusicCommand;
import nl.underkoen.underbot.utils.KeyLoaderUtil;
import nl.underkoen.underbot.utils.RoleUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Under_Koen on 18-04-17.
 */
public class Main {

    public static JDA jda;
    public static CommandHandler handler;
    public static HitboxUtil hitboxUtil;

    public static KeyLoaderUtil keys;

    public static String version = "0.3.1";

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            File file = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            file = new File(file.getParent() + "/Keys.json");
            if (!file.exists()) {
                file.createNewFile();
                try {
                    InputStream input = Main.class.getClassLoader().getResourceAsStream("Keys.json");
                    FileUtils.copyInputStreamToFile(input, file);
                    System.out.println("You need to hava a Keys.json file or a path to a Keys.json file as arg.");
                    System.out.println("Created a Keys.json.");
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            keys = new KeyLoaderUtil(FileUtils.readFileToString(file, Charset.defaultCharset()));
        } else {
            File file = new File(args[0]);
            if (!file.exists()) {
                System.out.print("You need to hava a Keys.json file or a path to a Keys.json file as arg.");
                return;
            }
            keys = new KeyLoaderUtil(FileUtils.readFileToString(file, Charset.defaultCharset()));
        }
        hitboxUtil = new HitboxUtil();

        handler = new CommandHandler("/");
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(keys.getDiscordKey())
                    .addEventListener(handler)
                    .buildBlocking();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //for (Role role : jda.getGuilds().get(0).getRoles()) {
        //    System.out.println(role.getName() + " -=- " + role.getPosition());
        //}

        initializeAllCommands("nl.underkoen.underbot.commands", handler);
        handler.initializeCommand(new MusicCommand());
        handler.initializeCommand(new MinesweeperCommand());
        jda.getPresence().setGame(Game.of("/help -> for help"));

        hitboxUtil.addListener(new LinkDiscord());
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
