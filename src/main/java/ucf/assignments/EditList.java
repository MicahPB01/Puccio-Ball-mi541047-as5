package ucf.assignments;
/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Micah Puccio-Ball
 *  Tasks are saved as...
 *  Name::Description::DueDate::Status
 */

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;

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

        String ext = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")+1);
        String currentLine;
        String[] properties;

        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            currentLine = bufferedReader.readLine();


            if(ext.equalsIgnoreCase("txt")) {
                while (currentLine != null) {
                    System.out.printf("%s\n", currentLine);
                    properties = currentLine.split("\t");
                    ItemObject tempItem = new ItemObject(properties[0], properties[1], properties[2]);
                    itemsInList.add(tempItem);
                    currentLine = bufferedReader.readLine();
                }
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
                properties = currentLine.split("\t");
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
                properties = currentLine.split("\t");
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
        System.out.println("Entered save with " + path);
        //prompt user for save location
        //copy current list into chosen location

        String ext;

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Inventory", ".txt, .html");
        fileChooser.getExtensionFilters().add(extFilter);


        fileChooser.setTitle("Select save location");
        System.out.println("Attempting to save");
        boolean exists = false;
        File save;
        if(TEST.equalsIgnoreCase("TEST::::::TEST"))   {
            System.out.println("Saving test");
            save = new File("SaveTest.txt");
        }
        else {
            System.out.println("Saving as new");
            save = fileChooser.showSaveDialog(new Stage());
        }
        File load = new File(path);
        System.out.println("checking file extension");
        System.out.println("File is " + save.getAbsolutePath());
        ext = save.getAbsolutePath().substring(save.getAbsolutePath().lastIndexOf(".")+1);
        System.out.println("File extension is" + ext);

        if(ext.equalsIgnoreCase( "html"))   {
            htmlSave(save.toString(), load);
        }
        if(ext.equalsIgnoreCase("txt"))    {
            tabSave(save.toString(), load);
        }
        Files.copy(load.toPath(), save.toPath());



        if(save.exists())   {
            exists = true;
        }
        System.out.println("Returning");
        return exists;
    }

    private boolean tabSave(String pathNew, File pathOld) throws IOException    {
        System.out.println("Entered tab save");
        ArrayList<ItemObject> data;
        data = getInfo(pathOld);
        File newFile = new File(pathNew);
        newFile.createNewFile();

        Path filePath = Path.of(pathNew);

        for(int i = 0; i < data.size(); i++) {
            try {
                System.out.println("Writing " + i + data.get(i).getValue() + data.get(i).getSerial()+ data.get(i).getName());
                Files.write(filePath, (data.get(i).getValue()
                        + "\t" + data.get(i).getSerial()
                        + "\t" + data.get(i).getName() + "\n").getBytes(), StandardOpenOption.APPEND);;
            }
            catch(Exception ignored)   {

            }
        }
        return false;
    }

    private boolean htmlSave(String pathNew, File pathOld) throws IOException {
        System.out.println("Entered html save");
        ArrayList<ItemObject> data;
        data = getInfo(pathOld);
        File newFile = new File(pathNew);
        newFile.createNewFile();

        Path filePath = Path.of(pathNew);

        try {
            Files.write(filePath, Collections.singleton("<style type=\"text/css\">\n.tg  {border-collapse:collapse;border-spacing:0;}\n " +
                    (".tg td{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;\n " +
                            "overflow:hidden;padding:10px 5px;word-break:normal;}\n " +
                            (".tg th{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;\n " +
                                    "font-weight:normal;overflow:hidden;padding:10px 5px;word-break:normal;}\n " +
                                    ".tg .tg-0lax{text-align:left;vertical-align:top}\n " +
                                    "</style>\n " +
                                    "<table class=\"tg\">\n " +
                                    "<thead>\n " +
                                    "<tr>"))));
        }
        catch(Exception e)   {
            System.out.println("Failed");
        }


        for(int i = 0; i < data.size(); i++) {

            try {
                Files.write((filePath), ("  <th class=\"tg-0lax\">"
                        + data.get(i).getValue()
                        + "<br></th>\n  <th class=\"tg-0lax\">" + data.get(i).getSerial()
                        + "<br></th>\n  <th class=\"tg-0lax\">" + data.get(i).getName()
                        +  "\"<br></th>\n").getBytes(), StandardOpenOption.APPEND);
                Files.write((filePath), ("  </tr>\n" +
                        "</thead>\n" +
                        "<tbody>\n" +
                        "  <tr>\n").getBytes(), StandardOpenOption.APPEND);
            }
            catch (Exception ignored) {
            }
        }

        try {
            Files.write((filePath), ("</tbody>\n" +
                    "</table>").getBytes(), StandardOpenOption.APPEND);
        }
        catch (Exception ignored) {
        }

        return false;
    }


}
