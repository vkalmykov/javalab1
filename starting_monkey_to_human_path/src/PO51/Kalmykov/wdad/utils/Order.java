package PO51.Kalmykov.wdad.utils;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private Officiant officiant;
    private List<Item> items;

    public Order() {
    }

    public Order(Officiant officiant, List<Item> items) {
        this.officiant = officiant;
        this.items = items;
    }

    public Officiant getOfficiant() {
        return officiant;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setOfficiant(Officiant officiant) {
        this.officiant = officiant;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
}
