package net.beetonia.minigame.gravity.util;

public class TimeUtils {

    public static String formatTimeMMSS(long seconds) {
        return String.format("%02d:%02d", (seconds % 3600) / 60, seconds % 60);
    }

}
