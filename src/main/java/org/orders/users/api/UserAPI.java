package org.orders.users.api;

import org.bukkit.entity.Player;
import org.orders.Main;
import org.orders.users.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserAPI {

    private Main plugin;

    private HashMap<String, User> userHashMap;

    public UserAPI() {
        this.plugin = Main.getInstance();
        onLoad();
    }

    public void onLoad() {
        userHashMap = new HashMap<>();
    }

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
