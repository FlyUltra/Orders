package org.orders.logger.api;

import org.orders.Main;
import org.orders.logger.Log;
import org.orders.orders.Figure;
import org.orders.orders.Order;

public class LogAPI {

    private Main plugin;

    public LogAPI() {
        this.plugin = Main.getInstance();
    }

    /**
     *
     * When someone complete order, we send log to db
     *
     * date is String because we use simple date format, not long for showing date
     * if someone looks into the log database
     * they will see the date immediately and won't need to use some converter
     *
     */
    public void sendLog(String name, Figure figure, Order order, String date) {
        Log log = new Log(name, figure.getId().toLowerCase(), order.getId().toLowerCase(), date);
        plugin.getHikariHandler().newLog(log);
    }



}
