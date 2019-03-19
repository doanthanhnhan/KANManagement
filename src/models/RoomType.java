/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Doan Thanh Nhan
 */
public class RoomType {

    private StringProperty type;
    private FloatProperty price;
    private FloatProperty discount;
    private BooleanProperty active;

    public RoomType() {
        this.active = new SimpleBooleanProperty();
        this.discount = new SimpleFloatProperty();
        this.price = new SimpleFloatProperty();
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

    public final float getPrice() {
        return price.get();
    }

    public final void setPrice(float value) {
        price.set(value);
    }

    public FloatProperty priceProperty() {
        return price;
    }

    public final float getDiscount() {
        return discount.get();
    }

    public final void setDiscount(float value) {
        discount.set(value);
    }

    public FloatProperty discountProperty() {
        return discount;
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
