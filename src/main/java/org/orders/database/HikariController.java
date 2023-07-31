package org.orders.database;


import org.orders.Main;

import java.sql.SQLException;

public class HikariController {
    public static Hikari DATABASE;

    private HikariHandler hikariHandler;
    private Main plugin;

    /**
     * Setup the database
     *
     * @param plugin Main class instance
     * @return Whether it was successful
     */
    public boolean setup(Main plugin) {
        this.plugin = plugin;
        DATABASE = new Hikari();
        this.hikariHandler = new HikariHandler(plugin);
        create();
        return true;
    }

    /*-----------------------------------------------------------------------------*/

    private void create() {
        try {

            String log = "CREATE TABLE IF NOT EXISTS `"
                    + DatabaseOptions.LOG_TABLE + "` (`"
                    + DatabaseOptions.LOG_ID + "` int NOT NULL AUTO_INCREMENT, `"
                    + DatabaseOptions.PLAYER_NAME + "` varchar(150), `"
                    + DatabaseOptions.FIGURE_NAME + "` varchar(200), `"
                    + DatabaseOptions.ORDER_NAME + "` varchar(200), `"
                    + DatabaseOptions.DATE + "` varchar(200), PRIMARY KEY (`" + DatabaseOptions.LOG_ID + "`));";
            getDatabase().getConnection().prepareStatement(log).executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public HikariHandler getHikariHandler() {
        return hikariHandler;
    }

    public Hikari getDatabase() {
        return DATABASE;
    }

}
