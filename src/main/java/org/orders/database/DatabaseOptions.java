package org.orders.database;

import org.orders.Main;

public class DatabaseOptions {

    public static String TABLE_PREFIX = Main.getInstance().getConfig().getString("database.tablePrefix");


    public static String LOG_TABLE = TABLE_PREFIX + "log";
    public static String LOG_ID = "id";
    public static String PLAYER_NAME = "name";
    public static String FIGURE_NAME = "figureName";
    public static String ORDER_NAME = "orderName";
    public static String DATE = "date";

}
