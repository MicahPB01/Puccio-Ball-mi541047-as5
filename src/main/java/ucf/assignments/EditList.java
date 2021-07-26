package ucf.assignments;
/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Micah Puccio-Ball
 */

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

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

    public ArrayList<ItemObject> getInfo(File file) throws IOException {
        //use provided file to scan each line in
        //split each line by the tab character
        //return arraylist of items
        System.out.println("Entered LoadList.getInfo");
        ArrayList<ItemObject> itemsInList = new ArrayList<>();

        String ext = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")+1);
        String currentLine;
        String[] properties;

        if(ext.equalsIgnoreCase("html"))   {
            convertHTML(file);
            file = new File("tempHTML.txt");
        }

        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            currentLine = bufferedReader.readLine();

                while (currentLine != null) {
                    System.out.printf("%s\n", currentLine);
                    properties = currentLine.split("\t");
                    ItemObject tempItem = new ItemObject(Integer.parseInt(properties[0]) , properties[1], properties[2]);
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
        //use provided file and scan in each line
        //split the properties
        //if the third property contains the provided string, add it to an arraylist
        //return the arraylist
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
                ItemObject tempItem = new ItemObject(Integer.parseInt(properties[0]),properties[1],properties[2]);

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
        //use provided file and scan in each line
        //split the properties
        //if the second property contains the provided string, add it to an arraylist
        //return the arraylist
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
                ItemObject tempItem = new ItemObject(Integer.parseInt(properties[0]),properties[1],properties[2]);

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
        //open a file and write each item property to it line by line separated by tabs
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
                        + "\t" + data.get(i).getName() + "\n").getBytes(), StandardOpenOption.APPEND);
            }
            catch(Exception ignored)   {

            }
        }
        return false;
    }

    private boolean htmlSave(String pathNew, File pathOld) throws IOException {
        //create file and fill it with generic html code
        //loop thorugh the number of items adding each item to the table
        //end file with the rest of the html tag
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
                        +  "<br></th>\n").getBytes(), StandardOpenOption.APPEND);
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

    public void convertHTML(File file) throws IOException {
        //iterate through file
        //skip the first lines which include the format code
        //take 3 lines at a time to get the item properties
        //skip a few lines, take the next 3 and so on
        //create a temp file to use
        System.out.println("Entered convertHTML");
        File tempFile =  new File("tempHTML.txt");
        Path filePath  = Path.of(tempFile.getAbsolutePath());
        String currentLine = null;
        String[] properties = new String[3];
        char[] breakDown;
        tempFile.createNewFile();
        String rebuiltString = "";
        int numLines;
        int numItems;


        try (Stream<String> stream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
            numLines = (int) stream.count();
            stream.close();
        }

        numItems = (numLines - 11) % 3;

        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            for(int i = 0; i < 11; i++) {
                currentLine = bufferedReader.readLine();
                System.out.println("Skipping " + currentLine);
            }
            currentLine = bufferedReader.readLine();
            while (currentLine != null) {

                for (int k = 0; k < numItems; k++) {

                    for (int i = 0; i < 3; i++) {
                        System.out.println("Checking: " + currentLine);
                        rebuiltString = "";

                        breakDown = currentLine.toCharArray();
                        System.out.println("line is " + currentLine.length());
                        for (int j = 22; j < currentLine.length(); j++) {
                            System.out.println("Starting char is: " + breakDown[j]);
                            System.out.println("Adding " + breakDown[j] + " to " + rebuiltString);

                            if (Character.compare(breakDown[j], '<') == 0) {
                                break;
                            }

                            rebuiltString = rebuiltString + breakDown[j] + "";
                            System.out.println(rebuiltString);
                        }
                        properties[i] = rebuiltString.toString();
                        System.out.println("Found property: " + rebuiltString);
                        currentLine = bufferedReader.readLine();

                    }
                    System.out.println("Adding item");
                    Files.write(filePath, (properties[0] + "\t" + properties[1] + "\t" + properties[2] + "\n").getBytes(), StandardOpenOption.APPEND);

                    for(int i = 0; i < 4; i++)   {
                        currentLine = bufferedReader.readLine();
                    }
                }
            }



            bufferedReader.close();
        }
        catch(Exception ignored)   {

        }

    }




}
