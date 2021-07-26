package ucf.assignments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class EditItemTest {

    @Test
    void checkAddedItem() throws IOException {
        //create example to do list
        //create a txt which the test should match
        //call the addItem function passing ItemObject properties
        //once function is done, compare the text file properties to the properties passed into the function
        //test will pass if the txt file properties match the pre determined ones

        EditItem edit = new EditItem();
        File file = new File("TestAdd.txt");
        file.createNewFile();
        String[] properties = new String[3];
        String[] selectedProperties = new String[3];
        String currentLine;
        selectedProperties[0] = "1234";
        selectedProperties[1] = "2345234523";
        selectedProperties[2] = "MMicah";

        edit.addItem("TestAdd.txt", selectedProperties);
        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            currentLine = bufferedReader.readLine();
            while(currentLine != null)   {
                properties = currentLine.split("\t");

                currentLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(Exception ignore)   {

        }

        Assertions.assertArrayEquals(properties, selectedProperties);
        file.delete();


    }
    @Test
    void checkRemoveItem() throws IOException, InterruptedException {
        //create list with a sample task
        //call removeItem from EditItem class
        //if function returns true, the task has been removed
        EditItem edit = new EditItem();
        File file = new File("TestRemove.txt");
        file.createNewFile();
        String[] properties = new String[4];
        String[] selectedProperties = new String[4];
        String currentLine;
        selectedProperties[0] = "1234";
        selectedProperties[1] = "2345234523";
        selectedProperties[2] = "MMicah";

        edit.addItem("TestRemove.txt", selectedProperties);
        ItemObject testItem = new ItemObject(Integer.parseInt(selectedProperties[0]) ,selectedProperties[1], selectedProperties[2]);
        Assertions.assertEquals(true, edit.removeItem("TestRemove.txt", testItem));
        file.delete();

    }

    @Test
    void checkEditValue() throws IOException {
        //create sample task in sample list
        //call editDescription from EditItem class
        EditItem edit = new EditItem();
        File file = new File("TestEditDescription.txt");
        file.createNewFile();
        String[] properties = new String[3];
        String[] selectedProperties = new String[3];
        String currentLine;
        selectedProperties[0] = "1234";
        selectedProperties[1] = "2345234523";
        selectedProperties[2] = "MMicah";

        edit.addItem("TestEditDescription.txt", selectedProperties);
        ItemObject testItem = new ItemObject(Integer.parseInt(selectedProperties[0]),selectedProperties[1], selectedProperties[2]);
        Assertions.assertEquals(true, edit.editSerial("TestEditDescription.txt", testItem, "yuioyuioyu"));
        file.delete();

    }

    @Test
    void checkEditSerial() throws IOException {
        //create a sample task in sample list
        //call editDueDate fro EditItem class
        EditItem edit = new EditItem();
        File file = new File("TestEditDueDate.txt");
        file.createNewFile();
        String[] properties = new String[4];
        String[] selectedProperties = new String[4];
        String currentLine;
        selectedProperties[0] = "1234";
        selectedProperties[1] = "2345234523";
        selectedProperties[2] = "MMicah";

        edit.addItem("TestEditDueDate.txt", selectedProperties);
        ItemObject testItem = new ItemObject(Integer.parseInt(selectedProperties[0]),selectedProperties[1], selectedProperties[2]);
        Assertions.assertEquals(true, edit.editSerial("TestEditDueDate.txt", testItem, "1234123412"));
        file.delete();

    }

    @Test
    void checkEditName() throws IOException {
        //create a sample task in sample list
        //call editDueDate fro EditItem class
        EditItem edit = new EditItem();
        File file = new File("TestEditDueDate.txt");
        file.createNewFile();
        String[] properties = new String[3];
        String[] selectedProperties = new String[3];
        String currentLine;
        selectedProperties[0] = "1234";
        selectedProperties[1] = "2345234523";
        selectedProperties[2] = "MMicah";

        edit.addItem("TestEditDueDate.txt", selectedProperties);
        ItemObject testItem = new ItemObject(Integer.parseInt(selectedProperties[0]),selectedProperties[1], selectedProperties[2]);
        Assertions.assertEquals(true, edit.editName("TestEditDueDate.txt", testItem, "hmmmm"));
        file.delete();

    }

}