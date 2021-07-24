package ucf.assignments;
/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Micah Puccio-Ball
 *  Tasks are saved as...
 *  Name::Description::DueDate::Status
 */

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class EditList {
    public File loadList(String path) {
        System.out.println("Entered LoadList.loadList with path: " +path);
        //create new file chooser
        //allow user to select one or more files
        //return the selected file
        File file;
        if(path.equalsIgnoreCase("")) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select list to load");
            file = fileChooser.showOpenDialog(new Stage());
        }
        else   {
            file = new File(path);
        }

        return file;
    }

    public ArrayList<ItemObject> getInfo(File file) {
        System.out.println("Entered LoadList.getInfo");
        ArrayList<ItemObject> itemsInList = new ArrayList<>();

        String currentLine;
        String[] properties;

        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            currentLine = bufferedReader.readLine();
            while(currentLine != null)   {
                System.out.printf("%s\n",currentLine);
                properties = currentLine.split("::");
                ItemObject tempItem = new ItemObject(properties[0],properties[1],properties[2]);
                itemsInList.add(tempItem);
                currentLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(Exception ignored)   {

        }


        System.out.print("Here");
        return itemsInList;
    }

    public ArrayList<ItemObject> searchName(File file, String name) {
        System.out.println("Entered LoadList.searchName looking for "+name);
        ArrayList<ItemObject> itemsInList = new ArrayList<>();

        String currentLine;
        String[] properties;

        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            currentLine = bufferedReader.readLine();
            while(currentLine != null)   {
                System.out.printf("%s\n",currentLine);
                properties = currentLine.split("::");
                ItemObject tempItem = new ItemObject(properties[0],properties[1],properties[2]);

                if(properties[2].contains(name)) {
                    itemsInList.add(tempItem);
                }
                currentLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(Exception ignored)   {

        }

        return itemsInList;
    }

    public ArrayList<ItemObject> searchSerial(File file, String serial) {
        System.out.println("Entered LoadList.searchSerial");
        ArrayList<ItemObject> itemsInList = new ArrayList<>();

        String currentLine;
        String[] properties;

        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            currentLine = bufferedReader.readLine();
            while(currentLine != null)   {
                System.out.printf("%s\n",currentLine);
                properties = currentLine.split("::");
                ItemObject tempItem = new ItemObject(properties[0],properties[1],properties[2]);

                if(properties[1].contains(serial)) {
                    itemsInList.add(tempItem);
                }
                currentLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(Exception ignored)   {

        }

        return itemsInList;
    }

    public boolean saveList(String path, String TEST) throws IOException {
        //prompt user for save location
        //copy current list into chosen location
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select save location");
        boolean exists = false;
        File save;
        if(TEST.equalsIgnoreCase("TEST::::::TEST"))   {
            save = new File("SaveTest.txt");
        }
        else {
            save = fileChooser.showSaveDialog(new Stage());
        }
        File load = new File(path);
        Files.copy(load.toPath(), save.toPath());



        if(save.exists())   {
            exists = true;
        }
        return exists;
    }


}
