package org.orders;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.orders.commands.OrderCommand;
import org.orders.database.HikariController;
import org.orders.database.HikariHandler;
import org.orders.logger.api.LogAPI;
import org.orders.orders.api.OrderAPI;
import org.orders.smartinvs.InventoryManager;
import org.orders.users.api.UserAPI;
import org.orders.users.listener.UserListener;
import org.orders.utils.config.ConfigAPI;

import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private static InventoryManager invManager;

    @Getter
    private OrderAPI orderAPI;
    @Getter
    private LogAPI logAPI;
    @Getter
    private UserAPI userAPI;

    private ConfigAPI config;

    @Getter
    private HikariHandler hikariHandler;
    @Getter
    private HikariController hikariController;

    /*-----------------------------------------------------------------------------*/

    @Override
    public void onEnable() {
        instance = this;

        config = new ConfigAPI(this, "config");
        config.create();
        loadFiles(config.getConfig());

        if (!config.getConfig().getBoolean("database.enabled")) {
            sendLogger(Level.WARNING, "-|-|-|-|-|-|-|-|-|-");
            sendLogger(Level.WARNING, " ");
            sendLogger(Level.WARNING, "DATABASE IS NOT ALLOWED IN CONFIG!");
            sendLogger(Level.WARNING, " ");
            sendLogger(Level.WARNING, "-|-|-|-|-|-|-|-|-|-");
            Bukkit.getServer().shutdown();
            return;
        }

        setUpDatabase();
        hikariHandler = new HikariHandler(this);


        if (hikariController.getDatabase() == null) {
            sendLogger(Level.WARNING, "-|-|-|-|-|-|-|-|-|-");
            sendLogger(Level.WARNING, " ");
            sendLogger(Level.WARNING, "DATABASE CONNECTION CANNOT BE ESTABLISHED!");
            sendLogger(Level.WARNING, " ");
            sendLogger(Level.WARNING, "-|-|-|-|-|-|-|-|-|-");
            Bukkit.getServer().shutdown();
            return;
        }

        userAPI = new UserAPI();
        orderAPI = new OrderAPI();
        logAPI = new LogAPI();

        registerCommand(new OrderCommand(), "orders");
        registerListener(new UserListener());

        invManager = new InventoryManager(this);
        invManager.init();
    }

    /*-----------------------------------------------------------------------------*/

    @Override
    public void onDisable() {

        HikariController.DATABASE.shutdown();

    }

    /*-----------------------------------------------------------------------------*/

    public boolean setUpDatabase() {
        hikariController = new HikariController();
        return hikariController.setup(this);
    }

    /*-----------------------------------------------------------------------------*/

    public void loadFiles(FileConfiguration config) {
        for (String fileName: config.getStringList("files")) {
            ConfigAPI configAPI = new ConfigAPI(this, fileName);
            configAPI.create();
        }
    }

    /*-----------------------------------------------------------------------------*/

    public void sendLogger(Level level, String message) {
        getLogger().log(level, message);
    }

    /*-----------------------------------------------------------------------------*/

    private void registerCommand(CommandExecutor commandExecutor, String cmd) {
        Objects.requireNonNull(getCommand(cmd)).setExecutor(commandExecutor);}

    /*-----------------------------------------------------------------------------*/

    private void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    /*-----------------------------------------------------------------------------*/

    public static InventoryManager manager() { return invManager; }

    /*-----------------------------------------------------------------------------*/

    public ConfigAPI cfgAPI() {
        return config;
    }

    /*-----------------------------------------------------------------------------*/

}