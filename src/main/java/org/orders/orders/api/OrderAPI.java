package org.orders.orders.api;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.orders.Main;
import org.orders.orders.Figure;
import org.orders.orders.Order;
import org.orders.users.User;
import org.orders.utils.Utils;
import org.orders.utils.config.ConfigAPI;
import org.orders.utils.invs.Inventories;

import java.io.File;
import java.util.*;

public class OrderAPI {

    private Main plugin;

    private HashMap<String, Figure> figureHashMap;

    public OrderAPI() {
        this.plugin = Main.getInstance();
        onLoad();

    }

    /*-----------------------------------------------------------------------------*/

    /**
     *
     * Runnable for changing the date we need for our orders in figures
     *
     */
    public void runnable() {
        new BukkitRunnable() {
            String lastDate = plugin.getConfig().getString("date");
            String newDate = null;
            boolean firstOnPass = true;
            @Override
            public void run() {
                newDate = Utils.formatDate(System.currentTimeMillis());

                if (lastDate.equalsIgnoreCase(newDate) && !firstOnPass) {
                    return;
                }
                firstOnPass = false;

                plugin.getConfig().set("date", newDate);
                plugin.saveConfig();

                // We need here to clear users completed orders
                for (User user : plugin.getUserAPI().getUserHashMap().values()) {
                    if (user.getFinishedOrders().isEmpty()) {
                        continue;
                    }
                    user.setFinishedOrders(new ArrayList<>());
                    continue;
                }

                // Here we set id for figure current order
                for (Figure figure : figureHashMap.values()) {
                    List<Order> orderList = new ArrayList<>(figure.getOrders().values());
                    Random random = new Random();
                    int randomIndex = random.nextInt(figure.getOrders().values().size());

                    figure.setIdOfSelectedOrder(orderList.get(randomIndex).getId());
                    continue;
                }

            }
            // Every 25m
        }.runTaskTimer(plugin, 0, (20 * 60 * 25));
    }

    /*-----------------------------------------------------------------------------*/

    /**
     *
     * Here we load all orders, in our figures
     *
     */
    public void onLoad() {
        figureHashMap = new HashMap<>();

        List<File> resourceFiles = List.of(Objects.requireNonNull(plugin.getDataFolder().listFiles()));


        for (File file : resourceFiles) {
            if (file.getName().contains("plugin") || file.getName().contains("config")) {
                continue;
            }

            ConfigAPI configAPI = new ConfigAPI(plugin, file.getName().replace(".yml", ""));
            Figure figure = new Figure(configAPI.getConfig().getString("id"), configAPI.getConfig().getString("name"), configAPI.getConfig().getStringList("desc"), null, new HashMap<>());

            for (String orderName : configAPI.getConfig().getConfigurationSection("orders").getKeys(false)) {
                Order order = new Order(orderName,
                        Material.valueOf(configAPI.getConfig().getString("orders." + orderName + ".material")),
                        configAPI.getConfig().getStringList("orders." + orderName + ".desc"),
                        configAPI.getConfig().getString("orders." + orderName + ".reward"),
                        configAPI.getConfig().getInt("orders." + orderName + ".amount"));
                figure.getOrders().put(order.getId(), order);
            }
            figureHashMap.put(figure.getId().toLowerCase(), figure);
        }
        runnable();
    }

    /*-----------------------------------------------------------------------------*/

    /**
     *
     * Method for opening menu with figureID
     *
     */
    public void openMenu(Player player, String figureId) {
        Figure figure = getFigure(figureId);
        Inventories.getOrderMenu(figure.getName()).open(player, figureId);
    }

    public HashMap<String, Figure> getFigureMap() {
        return figureHashMap;
    }

    public Figure getFigure(String id) {
        return figureHashMap.get(id.toLowerCase());
    }
}
