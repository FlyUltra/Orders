package org.orders.users.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.orders.Main;

public class UserListener implements Listener {

    private Main plugin;

    public UserListener() {
        this.plugin = Main.getInstance();
    }

    /**
     *
     * Join event for creating user
     *
     * @param event join event, with priority on Monitor
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        plugin.getUserAPI().createUser(player);

    }

}
