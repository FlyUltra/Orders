package org.orders.users.api;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.orders.Main;
import org.orders.users.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserAPI {

    private Main plugin;

    @Getter
    private HashMap<String, User> userHashMap;

    public UserAPI() {
        this.plugin = Main.getInstance();
        onLoad();
    }

    public void onLoad() {
        userHashMap = new HashMap<>();
    }

    /*-----------------------------------------------------------------------------*/

    /**
     *
     * Here we look at hashmap if cointains player name
     * if not we create him new
     *
     */
    public void createUser(Player player) {
        User user = getUser(player);

        if (user == null) {
            user = new User(player.getName(), new ArrayList<>());
            userHashMap.put(user.getName().toLowerCase(), user);
        }

    }

    public User getUser(String name) {
        return userHashMap.get(name.toLowerCase());
    }

    public User getUser(Player player) {
        return userHashMap.get(player.getName().toLowerCase());
    }

}
