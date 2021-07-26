package ucf.assignments;
/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Micah Puccio-Ball
 */
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemObject {

    private final SimpleStringProperty serial;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty value;

    public String getSerial() {
        return serial.get();
    }

    public String getName() {
        return name.get();
    }

    public Integer getValue() {
        return value.get();
    }

    public ItemObject(Integer value, String serial, String name)   {
        this.value = new SimpleIntegerProperty(value);
        this.serial = new SimpleStringProperty(serial);
        this.name = new SimpleStringProperty(name);
    }


}
