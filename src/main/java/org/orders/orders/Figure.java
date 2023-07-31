package org.orders.orders;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Figure {

    private String id;
    private String name;
    private List<String> desc;

    private String idOfSelectedOrder;
    private HashMap<String, Order> orders;

    public Figure(String id, String name, List<String> desc, String idOfSelectedOrder, HashMap<String, Order> orders) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.idOfSelectedOrder = idOfSelectedOrder;
        this.orders = orders;
    }
}
