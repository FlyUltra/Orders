package org.orders.logger.api;

import org.orders.Main;
import org.orders.logger.Log;
import org.orders.orders.Figure;
import org.orders.orders.Order;

public class LogAPI {

    private Main plugin;

    public LogAPI() {
        this.plugin = Main.getInstance();
        onLoad();
    }

    public void onLoad() {

    }

    public void sendLog(String name, Figure figure, Order order, String date) {
        Log log = new Log(name, figure.getId().toLowerCase(), order.getId().toLowerCase(), date);
        plugin.getHikariHandler().newLog(log);
    }



}
