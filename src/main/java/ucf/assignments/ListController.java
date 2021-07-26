package ucf.assignments;
/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Micah Puccio-Ball
 */

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ListController extends List {
    public TableColumn<ItemObject, Integer> ItemValue;
    public TableColumn<ItemObject, String> ItemSerial;
    public TableColumn<ItemObject, String> ItemName;
    public TableView<ItemObject> itemTable;
    public TextField addItemValue;
    public Text pathToFile;
    public TextField addItemSerial;
    public Button loadListButton;
    public TextField updateItemName;
    public TextField addItemName;
    public TextField nameText;
    public TextField serialText;
    public TextField addItemDescription;
    public TextField updateSerial;
    public TextField updateValue;
    public TextField updateName;


    @FXML
    public void addNewItemClick() throws IOException {
        //grab info from text boxes
        //if no file is loaded, create a new file
        //Call addItem in editItem class passing the current file path and the properties of the object
        //reload the table
        boolean result = false;
        EditItem edit = new EditItem();
        boolean check;
        String path;
        File file = new File("tempHTML.txt");

        String[] properties = new String[3];
        properties[0] = addItemValue.getText();
        properties[1] = addItemSerial.getText();
        properties[2] = addItemName.getText();

        if(file.exists())   {
            path = "tempHTML.txt";
        }
        else   {
            path = pathToFile.getText();
        }

        
        if(pathToFile.getText().equalsIgnoreCase(""))   {
            File newFile = new File("Inventory.txt");
            newFile.createNewFile();
            pathToFile.setText(newFile.getAbsolutePath());
        }
        check = edit.checkSerial(path, properties[1]);
        
        if(!check)   {
            System.out.println("Adding");
            result = edit.addItem(path, properties);
        }
        else {
            Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
            noSelection.setTitle("Unable to add Item!");
            noSelection.setHeaderText("Please check item requirements");
            noSelection.setContentText("Please ensure the Serial matches \"XXXXXXXXXX\"\nPlease ensure serial is unique!\n");
            noSelection.show();
        }


        if(!result)   {
            System.out.println("Loading");
        }
        else   {
            Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
            noSelection.setTitle("Unable to add Item!");
            noSelection.setHeaderText("Please check item requirements");
            noSelection.setContentText("Please ensure the Serial matches \"XXXXXXXXXX\"\nPlease ensure serial is unique!\nPlease ensure the value is a number!,");
            noSelection.show();
        }
        loadHelper();


    }
    @FXML
    public void removeItemClick() throws IOException, InterruptedException {
        //get info of selected item in list
        //Call removeItem in editItem class passing the itemObject
        //if no item is selected, throw up a message
        //reload the table

        EditItem edit = new EditItem();

        if(itemTable.getSelectionModel().getSelectedItem()!=null)    {
            ItemObject selectedObject = itemTable.getSelectionModel().getSelectedItem();
            edit.removeItem(pathToFile.getText(), selectedObject);
        }
        else   {
            Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
            noSelection.setTitle("Unable to remove task!");
            noSelection.setHeaderText("No task selected!");
            noSelection.setContentText("Please select a task!");
            noSelection.show();
        }
        loadHelper();
    }

    @FXML
    public void editSerialClick() throws IOException {
        //call editDescription  in EditItem class passing Path, selected itemObject and the new description
        //throw up message if no item is selected
        //reload the table
        boolean check;
        EditItem edit = new EditItem();
        String path;
        File file = new File("tempHTML.txt");

        if(file.exists())   {
            path = "tempHTML.txt";
        }
        else   {
            path = pathToFile.getText();
        }

        if(itemTable.getSelectionModel().getSelectedItem()!=null)    {
            ItemObject selectedObject = itemTable.getSelectionModel().getSelectedItem();
            check = edit.editSerial(path, selectedObject, updateSerial.getText());
            if(check)   {
                edit.editSerial(path, selectedObject, updateSerial.getText());
            }
            else   {
                Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
                noSelection.setTitle("Unable to edit Serial!");
                noSelection.setHeaderText("Requirements not met!");
                noSelection.setContentText("Serial must be unique!\n");
                noSelection.show();
            }
        }
        else   {
            Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
            noSelection.setTitle("Unable to edit Serial!");
            noSelection.setHeaderText("No item selected!");
            noSelection.setContentText("Please select an Item");
            noSelection.show();
        }
        updateSerial.setText("");
        loadHelper();
    }


    @FXML
    public void searchSerialClick() {
        //call load.searchSerial from LoadList class passing the path stored in a text field
        //convert resulting arraylist into an observable arraylist
        //push data from the list to the table
        //reload table

        EditList load = new EditList();
        File file = load.loadList(pathToFile.getText());
        ArrayList<ItemObject> items = load.searchSerial(file, serialText.getText());
        ObservableList<ItemObject> observableItems = FXCollections.observableArrayList(items);

        ItemValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        ItemSerial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        ItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        serialText.setText("");
        itemTable.setItems(observableItems);
    }
    @FXML
    public void searchNameClick() {
        //simply grab and show everything in txt file
        EditList load = new EditList();
        File file = load.loadList(pathToFile.getText());
        ArrayList<ItemObject> items = load.searchName(file, nameText.getText());
        ObservableList<ItemObject> observableItems = FXCollections.observableArrayList(items);

        ItemValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        ItemSerial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        ItemName.setCellValueFactory(new PropertyValueFactory<>("name"));

        itemTable.setItems(observableItems);
        nameText.setText("");
    }


    @FXML
    public void loadListClick() throws IOException {
        //set the in use file path to nothing to force program to change path
        pathToFile.setText("");

        ItemValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        ItemSerial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        ItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemTable.setItems(null);
        loadHelper();
    }

    public void loadHelper() throws IOException {
        System.out.println("Entered load helper");
        //get current file path
        //call load from the LoadList class passing the path
        //convert resulting arraylist into observable array list
        //clear text fields
        //push data to table

        EditList load = new EditList();
        File file = load.loadList(pathToFile.getText());
        File tempFile = new File("tempHTML.txt");


        pathToFile.setText(file.getPath());
        ArrayList<ItemObject> items = load.getInfo(file);
        ObservableList<ItemObject> observableItems = FXCollections.observableArrayList(items);
        addItemValue.setText("");
        addItemSerial.setText("");
        addItemName.setText("");


        ItemValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        ItemSerial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        ItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemTable.setItems(observableItems);
        if(tempFile.exists())   {
            PrintWriter writer = new PrintWriter(tempFile);
            writer.print("");
            writer.close();
        }

    }




    public void saveListAsClick() throws IOException {
        //have user select new place to save
        //copy current in use file to the placed they decided to save
        EditList save = new EditList();
        save.saveList(pathToFile.getText(), "");
        loadHelper();

    }

    public void editNameClick(ActionEvent actionEvent) throws IOException {
        //get new value
        //call editName from EditItem class passing Path, the selected object, and the new value
        //throw up a message if no task is selected
        //reload the table
        boolean check;
        EditItem edit = new EditItem();

        if(itemTable.getSelectionModel().getSelectedItem()!=null)    {
            ItemObject selectedObject = itemTable.getSelectionModel().getSelectedItem();
            check = edit.editName(pathToFile.getText(), selectedObject, updateName.getText());
            if(!check)   {
                Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
                noSelection.setTitle("Unable to edit Name!");
                noSelection.setHeaderText("Name does not meet requirements!");
                noSelection.setContentText("Name should be at least 2 characters long!\n");
                noSelection.show();
            }
        }
        else   {
            Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
            noSelection.setTitle("Unable to edit Name!");
            noSelection.setHeaderText("No item selected!");
            noSelection.setContentText("Please select an Item");
            noSelection.show();
        }
        updateName.setText("");
        loadHelper();
    }

    public void editValueClick(ActionEvent actionEvent) throws IOException {
        //get new value
        //call editValue from EditItem class passing Path, the selected object, and the new value
        //throw up a message if no task is selected
        //reload the table
        boolean check;
        EditItem edit = new EditItem();

        if(itemTable.getSelectionModel().getSelectedItem()!=null)    {
            ItemObject selectedObject = itemTable.getSelectionModel().getSelectedItem();
            check = edit.editValue(pathToFile.getText(), selectedObject, updateValue.getText());
            if(!check)   {
                Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
                noSelection.setTitle("Unable to edit Value!");
                noSelection.setHeaderText("Value does not meet requirements!");
                noSelection.setContentText("Value must be numerical!\n");
                noSelection.show();
            }
        }
        else   {
            Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
            noSelection.setTitle("Unable to edit Value!");
            noSelection.setHeaderText("No item selected!");
            noSelection.setContentText("Please select an Item");
            noSelection.show();
        }
        updateValue.setText("");
        loadHelper();
    }

    public void showAllClick(ActionEvent actionEvent) throws IOException {
        //load list
        loadHelper();
    }

}
