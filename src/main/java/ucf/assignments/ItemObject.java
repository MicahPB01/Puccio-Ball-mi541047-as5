package ucf.assignments;
/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Micah Puccio-Ball
 *  Tasks are saved as...
 *  Name::Description::DueDate::Status
 */
import javafx.beans.property.SimpleStringProperty;

public class ItemObject {

    private final SimpleStringProperty serial;
    private final SimpleStringProperty name;
    private final SimpleStringProperty value;

    public String getSerial() {
        return serial.get();
    }

    public String getName() {
        return name.get();
    }

    public String getValue() {
        return value.get();
    }

    public ItemObject(String value, String serial, String name)   {
        this.value = new SimpleStringProperty(value);
        this.serial = new SimpleStringProperty(serial);
        this.name = new SimpleStringProperty(name);
    }


}
