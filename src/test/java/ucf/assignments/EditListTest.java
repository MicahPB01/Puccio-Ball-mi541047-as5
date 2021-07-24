package ucf.assignments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class EditListTest {

    @Test
    void checkLoadList() throws IOException {
        //call loadList function passing a test txt file
        //try to load the created txt file
        //see if created txxt file is the file that is loaded
        File file = new File("test.txt");
        file.createNewFile();
        EditList load = new EditList();
        load.loadList("test.txt");
        Assertions.assertEquals(file, load.loadList("test.txt"));
        file.delete();
    }

    @Test
    void checkGetInfo() throws IOException {
        //create sample tasks in a sample list
        //call getInfo in LoadList class
        //call matches function from EditItem to see if the two items are identical
        EditItem edit = new EditItem();
        EditList load = new EditList();
        String[] properties = new String[3];
        properties[0] = "Test";
        properties[1] = "Test";
        properties[2] = "2000/10/10";
        File file = new File("TestInfo.txt");
        file.createNewFile();
        ItemObject testItem = new ItemObject("Test", "Test", "2000/10/10");

        edit.addItem("TestInfo.txt", properties);
        ItemObject actualItem = load.getInfo(file).get(0);

        Assertions.assertEquals(true, edit.matches(actualItem, testItem));
        file.delete();
    }

    @Test
    void checkGetCompletedInfo() throws IOException {
        //create sample tasks in a sample list
        //call getCompletedInfo in LoadList class
        //call matches function from EditItem to see if the item selected is indeed marked as completed
        File file = new File("TestComplete.txt");
        file.createNewFile();
        EditItem edit = new EditItem();
        EditList load = new EditList();
        String[] properties1 = new String[3];
        String[] properties2 = new String[3];
        properties1[0] = "Test1";
        properties2[0] = "Test2";
        properties1[1] = "Test";
        properties2[1] = "Test";
        properties1[2] = "2000/10/10";
        properties2[2] = "2000/10/10";
        ItemObject testItem1 = new ItemObject("Test1", "Test", "2000/10/10");
        ItemObject testItem2 = new ItemObject("Test2", "Test", "2000/10/10");

        edit.addItem("TestComplete.txt", properties1);
        edit.addItem("TestComplete.txt", properties2);
        edit.markComplete("TestComplete.txt", testItem2);
        testItem2.setStatus("Complete");

        ArrayList<ItemObject> actualItems = load.getCompletedInfo(file);
        Assertions.assertEquals(true, edit.matches(testItem2, actualItems.get(0)));
        file.delete();

    }

    @Test
    void checkGetIncompleteInfo() throws IOException {
        //create sample tasks in a sample list
        //call getCompletedInfo in LoadList class
        //call matches function from EditItem to see if the item selected is indeed marked as Incomplete
        File file = new File("TestIncomplete.txt");
        file.createNewFile();
        EditItem edit = new EditItem();
        EditList load = new EditList();
        String[] properties1 = new String[3];
        String[] properties2 = new String[3];
        properties1[0] = "Test1";
        properties2[0] = "Test2";
        properties1[1] = "Test";
        properties2[1] = "Test";
        properties1[2] = "2000/10/10";
        properties2[2] = "2000/10/10";
        ItemObject testItem1 = new ItemObject("Test1", "Test", "2000/10/10");
        ItemObject testItem2 = new ItemObject("Test2", "Test", "2000/10/10");

        edit.addItem("TestIncomplete.txt", properties1);
        edit.addItem("TestIncomplete.txt", properties2);
        edit.markComplete("TestIncomplete.txt", testItem2);
        testItem2.setStatus("Complete");

        ArrayList<ItemObject> actualItems = load.searchSerial(file);
        Assertions.assertEquals(true, edit.matches(testItem1, actualItems.get(0)));
        file.delete();
    }

    @Test
    void checkSave() throws IOException {
        //create file
        //call saveList in EditList class
        //verify the file is saved in the right location
        File file = new File("SaveTest.txt");
        file.createNewFile();
        EditList save = new EditList();
        Assertions.assertEquals(true, save.saveList("SaveTest.txt", "TEST::::::TEST"));
        file.delete();
    }

    @Test
    void checkRemoveAll() throws IOException {
        //create file
        //call removeAll in EditList class
        //assert true the result of removeAll
        EditList remove = new EditList();
        File file = new File("TestDelete.txt");
        file.createNewFile();

        Assertions.assertEquals(true, remove.removeAll("TestDelete.txt"));
        file.delete();


    }

}