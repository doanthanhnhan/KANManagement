/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.math.BigDecimal;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Doan Thanh Nhan
 */
public class RoomType {

    private StringProperty type;
    private BigDecimal price;
    private BigDecimal discount;
    private BooleanProperty active;

    public RoomType() {
        this.active = new SimpleBooleanProperty();
        this.type = new SimpleStringProperty();
    }

    public final String getType() {
        return type.get();
    }

    public final void setType(String value) {
        type.set(value);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public final boolean isActive() {
        return active.get();
    }

    public final void setActive(boolean value) {
        active.set(value);
    }

    public BooleanProperty activeProperty() {
        return active;
    }

}
