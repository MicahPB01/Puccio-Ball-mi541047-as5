package ucf.assignments;
/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Micah Puccio-Ball
 *  Tasks are saved as...
 *  Name::Description::DueDate::Status
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class FileIO {


    public void writeTempFile(ArrayList<String> items) throws IOException {
        System.out.println("Entered writetempfile");
        //create a temp file
        //write the objects to the temp file
        Path filePath = Path.of(createTempFile());
        try   {
            for (String item : items) {
                Files.write((filePath), item.getBytes(), StandardOpenOption.APPEND);
                Files.write((filePath), "\n".getBytes(), StandardOpenOption.APPEND);
            }
        }
        catch(Exception ignored)   {

        }
    }

    public void removeOldFile(String oldFilePath) {
        System.out.println("Entered removeoldfile");
        File oldFile = new File(oldFilePath);
        oldFile.delete();
    }

    public void renameFile(String oldPath, String newPath)   {
        System.out.println("entered renamefile");
        File newFile = new File(newPath);
        newFile.renameTo(new File(oldPath));
    }

    public String createTempFile() throws IOException {
        System.out.println("entered creatempfile");
        File newFile = new File("temp.txt");
        newFile.createNewFile();
        return newFile.getAbsolutePath();
    }

}
