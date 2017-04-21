package nl.UnderKoen.UnderBot.utils;

import java.awt.*;

/**
 * Created by Under_Koen on 21-04-17.
 */
public class ColorUtil {
    public static Color hexToColor(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }
}
