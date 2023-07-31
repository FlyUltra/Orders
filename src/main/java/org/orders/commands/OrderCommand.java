package org.orders.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.orders.Main;

import java.util.Set;

public class OrderCommand implements CommandExecutor {

    private Main plugin;

    public OrderCommand()  {
        this.plugin = Main.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            Set<String> figuresName = plugin.getOrderAPI().getFigureMap().keySet();

            if (figuresName.contains(args[0].toLowerCase())) {
                plugin.getOrderAPI().openMenu(player, args[0].toLowerCase());
                return true;
            }

        }

        player.sendMessage("Špatný příkaz");
        return true;
    }
}
