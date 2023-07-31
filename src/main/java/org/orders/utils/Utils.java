package org.orders.utils;

import org.orders.utils.colorAPI.ColorAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utils {

    /*-----------------------------------------------------------------------------*/

    public static String colorize(String message) {
        return ColorAPI.colorize(message);
    }

    /*-----------------------------------------------------------------------------*/

    public static int getRandomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Minimální hodnota musí být menší než maximální hodnota.");
        }

        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    /*-----------------------------------------------------------------------------*/

    public static String formatDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


}
