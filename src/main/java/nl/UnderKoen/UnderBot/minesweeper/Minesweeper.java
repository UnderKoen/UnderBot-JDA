package nl.UnderKoen.UnderBot.minesweeper;

import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import nl.UnderKoen.UnderBot.utils.Messages.TextMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Under_Koen on 21-07-17.
 */
public class Minesweeper {
    public static List<Minesweeper> games = new ArrayList<>();

    User owner;
    Map map;

    public Minesweeper(User owner) {
        this.owner = owner;
        map = new Map();
        games.remove(getGame(owner));
        games.add(this);
    }

    public static Minesweeper getGame(User owner) {
        for (Minesweeper game: games) {
            if (game.getOwner() == owner) return game;
        }
        return null;
    }

    public Map getMap() {
        return map;
    }

    public User getOwner() {

        return owner;
    }

    public void sendMap(TextChannel channel) {
        new TextMessage().setTitle("MineSweeper").addText(map.toMessage()).setMention(owner).sendMessage(channel);
    }
}