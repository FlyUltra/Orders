package org.orders.utils.invs;

import org.orders.Main;
import org.orders.inventories.OrderInv;
import org.orders.smartinvs.SmartInventory;
import org.orders.utils.Utils;

public class Inventories {

    /**
     *
     * Our order menu getter
     *
     */
    public static SmartInventory getOrderMenu(String figureName) {
        return SmartInventory.builder().manager(Main.manager())
                .id("order_menu")
                .provider(new OrderInv())
                .size(5, 9)
                .title(Utils.colorize("§3§lZakázka od " + figureName))
                .build();
    }

}
