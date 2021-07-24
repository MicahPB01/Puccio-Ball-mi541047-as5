package ucf.assignments;
/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Micah Puccio-Ball
 *  Tasks are saved as...
 *  Name::Description::DueDate
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListController extends List {
    public TableColumn<ItemObject, String> ItemValue;
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


    public void initialize ()    {
        File file = new File("Inventory.txt");
        if(file.exists())   {
            pathToFile.setText(file.getAbsolutePath());
            loadHelper();
        }
    }

    @FXML
    public void addNewItemClick() throws IOException {
        //grab info from text boxes
        //if no file is loaded, create a new file
        //Call addItem in editItem class passing the current file path and the properties of the object
        //reload the table
        boolean result;
        EditItem edit = new EditItem();

        String[] properties = new String[3];
        properties[0] = addItemValue.getText();
        properties[1] = addItemSerial.getText();
        properties[2] = addItemName.getText();

        if(pathToFile.getText().equalsIgnoreCase(""))   {
            File newFile = new File("Inventory.txt");
            newFile.createNewFile();
            pathToFile.setText(newFile.getAbsolutePath());
        }

         result = edit.addItem(pathToFile.getText(), properties);

        if(!result)   {
            System.out.println("Loading");
            loadHelper();
        }
        else   {
            Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
            noSelection.setTitle("Unable to add Item!");
            noSelection.setHeaderText("Please check item requirements");
            noSelection.setContentText("Please ensure the Serial matches \"XXXXXXXXXX\"\nPlease ensure serial is unique!\nPlease ensure the value is a number!,");
            noSelection.show();
        }



    }
    @FXML
    public void removeItemClick() throws IOException {
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

        if(itemTable.getSelectionModel().getSelectedItem()!=null)    {
            ItemObject selectedObject = itemTable.getSelectionModel().getSelectedItem();
            check = edit.editSerial(pathToFile.getText(), selectedObject, updateSerial.getText());
            if(!check)   {
                Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
                noSelection.setTitle("Unable to edit Serial!");
                noSelection.setHeaderText("Serial does not meet requirements!");
                noSelection.setContentText("Serial number should match \"XXXXXXXXXX\"\nSerial should be unique!\n");
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
        //call load getIncompleteInfo from LoadList class passing the path stored in a text field
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
    public void loadListClick() {
        //set the in use file path to nothing to force program to change path
        pathToFile.setText("");

        ItemValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        ItemSerial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        ItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemTable.setItems(null);
        loadHelper();
    }

    public void loadHelper() {
        System.out.println("Entered load helper");
        //get current file path
        //call load from the LoadList class passing the path
        //convert resulting arraylist into observable array list
        //clear text fields
        //push data to table

        EditList load = new EditList();
        File file = load.loadList(pathToFile.getText());
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

    }




    public void saveListAsClick() throws IOException {
        //have user select new place to save
        //copy current in use file to the placed they decided to save
        EditList save = new EditList();
        save.saveList(pathToFile.getText(), "");
        loadHelper();

    }

    public void editNameClick(ActionEvent actionEvent) throws IOException {
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

    public void showAllClick(ActionEvent actionEvent) {
        loadHelper();
    }
}
