package org.orders.logger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Log {

    private String playerName;

    private String figureName;
    private String orderName;

    private String date;

    public Log(String playerName, String figureName, String orderName, String date) {
        this.playerName = playerName;
        this.figureName = figureName;
        this.orderName = orderName;
        this.date = date;
    }

}
