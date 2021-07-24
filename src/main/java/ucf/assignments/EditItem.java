package ucf.assignments;

/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Micah Puccio-Ball
 *  Tasks are saved as...
 *  Name::Description::DueDate::Status
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;


public class EditItem {

    public boolean addItem(String path, String[] properties)   {
        //shorten description to 256 characters
        //add a line of text to txt file using item properties
        System.out.println("Entered EditItem.addItem");
        int length = 256;

        if(properties[2].length() < 2)   {
            System.out.println("Bad length");
            return true;
        }

        if(!properties[0].matches("[0-9]+"))   {
            System.out.println("Bad value");
            return true;
        }

        if(!properties[1].matches("[^_\\W]+") || properties[1].length() != 10)       {
            System.out.println("Bad Serial");
            return true;
        }

        if(properties[2].length() > 256)
        {
            properties[1] = properties[1].substring(0, length);
        }

        if(checkSerial(path, properties[1]))   {
            System.out.println("Serial Exists");
            return true;
        }

        Path filePath = Path.of(path);
        try   {
            Files.write((filePath), ("$" + properties[0] + "::" + properties[1] + "::" + properties[2]  + "\n").getBytes(), StandardOpenOption.APPEND);
        }
        catch(Exception ignored)   {
        }

        System.out.println("Returning false");
        return false;

    }

    public boolean checkSerial(String path, String serial)   {
        System.out.println("Checking serial in file" + path);
        String currentLine;
        String[] properties;
        File file = new File(path);
        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            currentLine = bufferedReader.readLine();
            while(currentLine != null)   {
                properties = currentLine.split("::");

                if(properties[1].equalsIgnoreCase(serial))   {
                    System.out.println("Found serial");
                    return true;
                }
                currentLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(Exception ignored) {}
        System.out.println("Didn't find serial");
        return false;
    }


    public boolean removeItem(String path, ItemObject item) throws IOException {
        //find an entry in the txt file the matches the object the user selected
        //when the line is found call to the FileIO class

        System.out.print("Entered EditItem.removeItem\n");
        boolean removed = false;
        FileIO change = new FileIO();
        String[] properties;
        String[] selectedProperties = new String[3];
        String currentLine;
        String newPath;
        selectedProperties[0] = item.getValue();
        selectedProperties[1] = item.getSerial();
        selectedProperties[2] = item.getName();
        ArrayList<String> newFileData = new ArrayList<>();

        File file = new File(path);
        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            currentLine = bufferedReader.readLine();
            while(currentLine != null)   {
                properties = currentLine.split("::");

                ItemObject tempItem = new ItemObject(properties[0],properties[1],properties[2]);

                if(!matches(item, tempItem))   {
                    newFileData.add(currentLine);
                    System.out.println("Didnt find item");
                }
                else   {
                    System.out.println("Found item");
                    removed = true;
                }
                currentLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(Exception ignored) {}
        change.writeTempFile(newFileData);
        newPath = change.createTempFile();
        change.removeOldFile(path);
        change.renameFile(path, newPath);

        return removed;
    }



    public boolean matches(ItemObject o1, ItemObject o2)   {
        //check to see if current object matches the selected object
        return o1.getValue().equals(o2.getValue()) && o1.getSerial().equals(o2.getSerial()) && o1.getName().equals(o2.getName());
    }

    public boolean editValue(String path, ItemObject item, String newValue) throws  IOException   {
        //check if serial is already taken
        //check if serial meets length/char requirement
        //Find the line in the txt file which matches the selected object
        //once the correct task is found, remove the line, replacing it with the same thing except with the new description
        //call to FileIO class
        System.out.print("Entered EditItem.editSerial\n");
        boolean updated = false;

        FileIO change = new FileIO();
        String[] properties;
        String[] selectedProperties = new String[3];
        String currentLine;
        String newPath;
        selectedProperties[0] = item.getValue();
        selectedProperties[1] = item.getSerial();
        selectedProperties[2] = item.getName();
        ArrayList<String> newFileData = new ArrayList<>();
        System.out.printf("Selected Item: %s %s %s\n",selectedProperties[0], selectedProperties[1], selectedProperties[2]);

        if(!newValue.matches("[0-9]+") || newValue.equalsIgnoreCase(""))   {
            System.out.println("Bad value");
            return false;
        }

        File file = new File(path);
        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            currentLine = bufferedReader.readLine();
            while(currentLine != null)   {
                properties = currentLine.split("::");

                ItemObject tempItem = new ItemObject(properties[0],properties[1],properties[2]);
                System.out.printf("Checking: %s %s %s\n",properties[0] , properties[1] , properties[2]);

                if(!matches(item, tempItem))   {
                    newFileData.add(currentLine);
                }
                else   {
                    System.out.println("Found");
                    currentLine = "$" + newValue+"::"+properties[1]+"::"+properties[2];
                    updated = true;
                    newFileData.add(currentLine);
                }
                currentLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(Exception ignored) {}
        change.writeTempFile(newFileData);
        newPath = change.createTempFile();
        change.removeOldFile(path);
        change.renameFile(path, newPath);

        return updated;
    }


    public boolean editSerial(String path, ItemObject item, String newSerial) throws IOException {
        //check if serial is already taken
        //check if serial meets length/char requirement
        //Find the line in the txt file which matches the selected object
        //once the correct task is found, remove the line, replacing it with the same thing except with the new description
        //call to FileIO class
        System.out.print("Entered EditItem.editSerial\n");
        boolean updated = false;

        FileIO change = new FileIO();
        String[] properties;
        String[] selectedProperties = new String[3];
        String currentLine;
        String newPath;
        selectedProperties[0] = item.getValue();
        selectedProperties[1] = item.getSerial();
        selectedProperties[2] = item.getName();
        ArrayList<String> newFileData = new ArrayList<>();
        System.out.printf("Selected Item: %s %s %s\n",selectedProperties[0], selectedProperties[1], selectedProperties[2]);

        if(checkSerial(path, newSerial))   {
            System.out.println("Bad Serial copy");
            return false;
        }

        if(!newSerial.matches("[^_\\W]+") || newSerial.length() != 10)       {
            System.out.println("Bad Serial length");

            return false;
        }

        File file = new File(path);
        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            currentLine = bufferedReader.readLine();
            while(currentLine != null)   {
                properties = currentLine.split("::");

                ItemObject tempItem = new ItemObject(properties[0],properties[1],properties[2]);
                System.out.printf("Checking: %s %s %s\n",properties[0] , properties[1] , properties[2]);

                if(!matches(item, tempItem))   {
                    newFileData.add(currentLine);
                }
                else   {
                    System.out.println("Found");
                    currentLine = properties[0]+"::"+newSerial+"::"+properties[2];
                    updated = true;
                    newFileData.add(currentLine);
                }
                currentLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(Exception ignored) {}
        change.writeTempFile(newFileData);
        newPath = change.createTempFile();
        change.removeOldFile(path);
        change.renameFile(path, newPath);

        return updated;
    }

    public boolean editName(String path, ItemObject item, String newName) throws IOException {
        //Find the line in the txt file which matches the selected object
        //once the correct task is found, remove the line, replacing it with the same thing except with the new due date
        //call to FileIO class
        System.out.print("Entered EditItem.editName\n");
        FileIO change = new FileIO();
        boolean updated = false;
        String[] properties;
        String[] selectedProperties = new String[4];
        String currentLine;
        String newPath;
        selectedProperties[0] = item.getValue();
        selectedProperties[1] = item.getSerial();
        selectedProperties[2] = item.getName();
        ArrayList<String> newFileData = new ArrayList<>();

        if(newName.length() < 2)    {
            return false;
        }

        File file = new File(path);
        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            currentLine = bufferedReader.readLine();
            while(currentLine != null)   {
                properties = currentLine.split("::");

                ItemObject tempItem = new ItemObject(properties[0],properties[1],properties[2]);
                System.out.printf("Checking: %s %s %s\n",properties[0] , properties[1] , properties[2]);

                if(!matches(item, tempItem))   {
                    newFileData.add(currentLine);
                }
                else   {
                    System.out.println("Found");
                    currentLine = properties[0]+"::"+properties[1]+"::"+newName;
                    updated = true;
                    newFileData.add(currentLine);
                }
                currentLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(Exception ignored) {}
        change.writeTempFile(newFileData);
        newPath = change.createTempFile();
        change.removeOldFile(path);
        change.renameFile(path, newPath);

        return updated;
    }



}
