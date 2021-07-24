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
        String[] properties = new String[4];
        String[] selectedProperties = new String[4];
        String currentLine;
        selectedProperties[0] = "Test task";
        selectedProperties[1] = "Test Description";
        selectedProperties[2] = "2021/7/10";
        selectedProperties[3] = "Incomplete";

        edit.addItem("TestAdd.txt", selectedProperties);
        try {
            FileReader input = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(input);

            currentLine = bufferedReader.readLine();
            while(currentLine != null)   {
                properties = currentLine.split("::");

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
    void checkRemoveItem() throws IOException {
        //create list with a sample task
        //call removeItem from EditItem class
        //if function returns true, the task has been removed
        EditItem edit = new EditItem();
        File file = new File("TestRemove.txt");
        file.createNewFile();
        String[] properties = new String[4];
        String[] selectedProperties = new String[4];
        String currentLine;
        selectedProperties[0] = "Test task";
        selectedProperties[1] = "Test Description";
        selectedProperties[2] = "2021/7/10";
        selectedProperties[3] = "Incomplete";

        edit.addItem("TestRemove.txt", selectedProperties);
        ItemObject testItem = new ItemObject(selectedProperties[0],selectedProperties[1], selectedProperties[2]);
        Assertions.assertEquals(true, edit.removeItem("TestRemove.txt", testItem));
        file.delete();

    }

    @Test
    void checkEditDescription() throws IOException {
        //create sample task in sample list
        //call editDescription from EditItem class
        EditItem edit = new EditItem();
        File file = new File("TestEditDescription.txt");
        file.createNewFile();
        String[] properties = new String[4];
        String[] selectedProperties = new String[4];
        String currentLine;
        selectedProperties[0] = "Test task";
        selectedProperties[1] = "Test Description";
        selectedProperties[2] = "2021/7/10";
        selectedProperties[3] = "Incomplete";

        edit.addItem("TestEditDescription.txt", selectedProperties);
        ItemObject testItem = new ItemObject(selectedProperties[0],selectedProperties[1], selectedProperties[2]);
        Assertions.assertEquals(true, edit.editSerial("TestEditDescription.txt", testItem, "Updated Description"));
        file.delete();

    }

    @Test
    void checkEditDueDate() throws IOException {
        //create a sample task in sample list
        //call editDueDate fro EditItem class
        EditItem edit = new EditItem();
        File file = new File("TestEditDueDate.txt");
        file.createNewFile();
        String[] properties = new String[4];
        String[] selectedProperties = new String[4];
        String currentLine;
        selectedProperties[0] = "Test task";
        selectedProperties[1] = "Test Description";
        selectedProperties[2] = "2021/7/10";
        selectedProperties[3] = "Incomplete";

        edit.addItem("TestEditDueDate.txt", selectedProperties);
        ItemObject testItem = new ItemObject(selectedProperties[0],selectedProperties[1], selectedProperties[2]);
        Assertions.assertEquals(true, edit.editName("TestEditDueDate.txt", testItem, "1010/7/10"));
        file.delete();

    }

    @Test
    void checkMarkComplete() throws IOException {
        //create a sample task in a sample list
        //call markComplete from EditItem class
        EditItem edit = new EditItem();
        File file = new File("TestComplete.txt");
        file.createNewFile();
        String[] properties = new String[4];
        String[] selectedProperties = new String[4];
        String currentLine;
        selectedProperties[0] = "Test task";
        selectedProperties[1] = "Test Description";
        selectedProperties[2] = "2021/7/10";
        selectedProperties[3] = "Incomplete";

        edit.addItem("TestComplete.txt", selectedProperties);
        ItemObject testItem = new ItemObject(selectedProperties[0],selectedProperties[1], selectedProperties[2]);
        Assertions.assertEquals(true, edit.markComplete("TestComplete.txt", testItem));
        file.delete();




    }

    @Test
    void checkMarkIncomplete() throws IOException {
        //create a sample list with sample text
        //set task status to complete
        //call markIncomplete in EditItem class
        EditItem edit = new EditItem();
        File file = new File("TestIncomplete.txt");
        file.createNewFile();
        String[] properties = new String[4];
        String[] selectedProperties = new String[4];
        String currentLine;
        selectedProperties[0] = "Test task";
        selectedProperties[1] = "Test Description";
        selectedProperties[2] = "2021/7/10";
        selectedProperties[3] = "Incomplete";

        edit.addItem("TestIncomplete.txt", selectedProperties);
        ItemObject testItem = new ItemObject(selectedProperties[0],selectedProperties[1], selectedProperties[2]);
        edit.markComplete("TestIncomplete.txt", testItem);
        testItem.setStatus("Complete");
        Assertions.assertEquals(true, edit.markIncomplete("TestIncomplete.txt", testItem));
        file.delete();
    }

}