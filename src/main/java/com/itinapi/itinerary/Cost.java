package com.itinapi.itinerary;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by hamehta3 on 10/15/16.
 */
@XmlRootElement
public class Cost {
    private Double amount;
    private String currency;

    public Cost() {}

    public Cost(Double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
