package org.orders.users;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {

    private String name;

    private List<String> finishedOrders;

    public User(String name, List<String> finishedOrders) {
        this.name = name;
        this.finishedOrders = finishedOrders;
    }

    public boolean isFinished(String fullName) {
        return finishedOrders.contains(fullName);
    }
}
