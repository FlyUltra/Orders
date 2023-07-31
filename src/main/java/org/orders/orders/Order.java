package org.orders.orders;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter
@Setter
public class Order {

    private String id;

    private Material material;

    private List<String> desc;
    private String reward;

    private int amount;

    public Order(String id, Material material, List<String> desc, String reward, int amount) {
        this.id = id;
        this.material = material;
        this.desc = desc;
        this.reward = reward;
        this.amount = amount;
    }
}
