package nl.underkoen.underbot.hitbox;

import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.awt.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Under_Koen on 04/11/2017.
 */
public interface RaffleMsg extends Response {
    default String getMethod() {return "raffleMsg";}

    String getChannel();

    String getQuestion();

    String getPrize();

    List<JsonObject> getChoices();

    Timestamp getStartTime();

    Boolean forAdmin();

    RaffleStatus getRaffleStatus();

    Color getNameColor();

    Boolean isSubscriberOnly();

    Boolean isFollowerOnly();
} enum RaffleStatus {
    started, paused, ended, hidden, delete;
}