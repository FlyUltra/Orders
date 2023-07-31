package org.orders.database;

import org.bukkit.Bukkit;
import org.orders.Main;
import org.orders.logger.Log;

public class HikariHandler {

    private Main plugin;

    public HikariHandler(Main plugin) {
        this.plugin = plugin;
    }


    public void newLog(Log log) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String insertSQL = "INSERT INTO `" + DatabaseOptions.LOG_TABLE +
                    "` (name, figureName, orderName, date) " +
                    "VALUES " +
                    "(?, ?, ?, ?)";
            HikariController.BLOCKSUMO.query(insertSQL,
                    log.getPlayerName(),
                    log.getFigureName(),
                    log.getOrderName(),
                    log.getDate());
        });
    }

}
