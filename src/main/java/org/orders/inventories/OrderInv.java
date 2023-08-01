package org.orders.inventories;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.orders.Main;
import org.orders.builder.ItemBuilder;
import org.orders.orders.Figure;
import org.orders.orders.Order;
import org.orders.smartinvs.ClickableItem;
import org.orders.smartinvs.content.InventoryContents;
import org.orders.smartinvs.content.InventoryProvider;
import org.orders.users.User;
import org.orders.utils.Utils;

public class OrderInv implements InventoryProvider {

    private Player player;

    /**
     *
     * Edited init method, where we have + figureId
     *
     */
    @Override
    public void init(Player player, String figureId, InventoryContents contents) {
        this.player = player;

        Figure figure = Main.getInstance().getOrderAPI().getFigure(figureId);
        User user = Main.getInstance().getUserAPI().getUser(player);

        if (figure == null) {
            player.closeInventory();
            player.sendMessage("Něco se nepovedlo!");
            return;
        }

        contents.fill(getBlackGlass());

        contents.set(0, 1, getRedGlass());
        contents.set(1, 1, getRedGlass());
        contents.set(2, 1, getRedGlass());
        contents.set(3, 1, getRedGlass());
        contents.set(4, 1, getRedGlass());

        contents.set(0, 7, getRedGlass());
        contents.set(1, 7, getRedGlass());
        contents.set(2, 7, getRedGlass());
        contents.set(3, 7, getRedGlass());
        contents.set(4, 7, getRedGlass());

        contents.set(1, 4, getHead(figure));
        contents.set(3, 3, getDescription(figure));
        contents.set(3, 5, getCheck(user,figure));

    }

    private ClickableItem getBlackGlass() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE);
        itemBuilder.setDisplayName(" ");
        return ClickableItem.empty(itemBuilder.build());
    }

    private ClickableItem getRedGlass() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.RED_STAINED_GLASS_PANE);
        itemBuilder.setDisplayName(" ");
        return ClickableItem.empty(itemBuilder.build());
    }

    private ClickableItem getHead(Figure figure) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
        itemBuilder.setDisplayName(Utils.colorize(figure.getName()));

        for (String str : figure.getDesc()) {
            itemBuilder.addLore(str);
        }

        return ClickableItem.empty(itemBuilder.build());
    }

    private ClickableItem getCheck(User user, Figure figure) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.EMERALD);
        itemBuilder.setDisplayName(Utils.colorize("{#00e6ca}&lSplnit"));
        itemBuilder.addLore("§7Pokud myslíš že máš splněno, klikni");

        return ClickableItem.of(itemBuilder.build(), e -> {
            Order order = figure.getOrders().get(figure.getIdOfSelectedOrder());

            if (user.isFinished(figure.getName().toLowerCase()+"-"+order.getId().toLowerCase())) {
                player.closeInventory();
                player.sendMessage("Jiz mas splneno!");
                return;
            }

            removeMaterial(player, figure, order);
        });

    }

    private ClickableItem getDescription(Figure figure) {
        Order order = figure.getOrders().get(figure.getIdOfSelectedOrder());

        ItemBuilder itemBuilder = new ItemBuilder(Material.PAPER);
        itemBuilder.setDisplayName(Utils.colorize("{#00e6ca}&lPopisek"));

        for (String str : order.getDesc()) {
            itemBuilder.addLore(str);
        }

        return ClickableItem.empty(itemBuilder.build());
    }

    /*-----------------------------------------------------------------------------*/

    /**
     *
     * Here we first check if player have amount material we want
     * if he has we remove it, and use complete method
     *
     */
    public void removeMaterial(Player player, Figure figure, Order order) {
        int requiredAmount = order.getAmount();
        int amountInInventory = getAmountInInventory(order, player.getInventory());

        if (amountInInventory < requiredAmount) {
            player.sendMessage("Mas nedostatek ktery je pozadovan!");
            return;
        }

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null || itemStack.getType() != order.getMaterial()) {
                continue;
            }

            int amountInStack = itemStack.getAmount();
            if (amountInStack <= requiredAmount) {
                requiredAmount -= amountInStack;
                itemStack.setAmount(0);
            } else {
                itemStack.setAmount(amountInStack - requiredAmount);
                break;
            }
        }

        complete(player, figure, order);
        player.updateInventory();
    }

    /*-----------------------------------------------------------------------------*/

    /**
     *
     * Here we send log request
     * We add info about finish into list
     * And after that we give reward
     *
     */
    public void complete(Player player, Figure figure, Order order) {

        String date = Utils.formatDate(System.currentTimeMillis());
        Main.getInstance().getLogAPI().sendLog(player.getName(), figure, order, date);

        User user = Main.getInstance().getUserAPI().getUser(player);
        user.getFinishedOrders().add(figure.getName().toLowerCase()+"-"+order.getId().toLowerCase());

        giveReward(player, order);
        player.sendMessage("Uspesne si odevzal material!");

    }

    /*-----------------------------------------------------------------------------*/

    /**
     *
     * Here we only give reward
     * "amount:material"
     *
     */
    public void giveReward(Player player, Order order) {
        String reward = order.getReward();
        String[] split = reward.split(":");

        Material material = Material.valueOf(split[1]);
        int amount = Integer.parseInt(split[0]);

        ItemStack itemStack = new ItemStack(material, amount);
        player.getInventory().addItem(itemStack);

    }

    /*-----------------------------------------------------------------------------*/

    /**
     *
     * Here we count required material in player inventory
     *
     */
    public int getAmountInInventory(Order order, Inventory inventory) {
        int amount = 0;

        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null || itemStack.getType() != order.getMaterial()) {
                continue;
            }

            amount += itemStack.getAmount();
        }

        return amount;
    }


}

