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
        //see if created txt file is the file that is loaded
        File file = new File("test.txt");
        file.createNewFile();
        EditList load = new EditList();
        load.loadList("test.txt");
        Assertions.assertEquals(file, load.loadList("test.txt"));
        file.delete();
    }

    @Test
    void checkGetInfo() throws IOException {
        //create sample item in a sample list
        //call getInfo in LoadList class
        //call matches function from EditItem to see if the two items are identical
        EditItem edit = new EditItem();
        EditList load = new EditList();
        String[] properties = new String[3];
        properties[0] = String.valueOf(15);
        properties[1] = "rtyurtyurt";
        properties[2] = "Test";
        File file = new File("TestInfo.txt");
        file.createNewFile();
        ItemObject testItem = new ItemObject(15, "rtyurtyurt", "Test");

        edit.addItem("TestInfo.txt", properties);
        ItemObject actualItem = load.getInfo(file).get(0);

        Assertions.assertEquals(true, edit.matches(actualItem, testItem));
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
    void checkSearchSerial() throws IOException {
        //create sample items in a sample list
        //call searchSerial in LoadList class
        //call matches function from EditItem to see if the item selected is found
        File file = new File("TestIncomplete.txt");
        file.createNewFile();
        EditItem edit = new EditItem();
        EditList load = new EditList();
        String[] properties1 = new String[3];
        String[] properties2 = new String[3];
        properties1[0] = String.valueOf(15);
        properties2[0] = String.valueOf(115);
        properties1[1] = "XXXXXXXXXX";
        properties2[1] = "ZZZZZZZZZZ";
        properties1[2] = "test";
        properties2[2] = "test";
        ItemObject testItem1 = new ItemObject(15, "XXXXXXXXXX", "test");
        ItemObject testItem2 = new ItemObject(15, "ZZZZZZZZZZ", "test");

        edit.addItem("TestIncomplete.txt", properties1);
        edit.addItem("TestIncomplete.txt", properties2);

        ArrayList<ItemObject> actualItems = load.searchSerial(file, "XXXXXXXXXX");
        Assertions.assertEquals(true, edit.matches(testItem1, actualItems.get(0)));
        Assertions.assertEquals(false, edit.matches(testItem2, actualItems.get(0)));
        file.delete();

    }

    @Test
    void checkSearchName() throws IOException {
        //create sample items in a sample list
        //call searchName in LoadList class
        //call matches function from EditItem to see if the item selected is found
        File file = new File("TestIncomplete.txt");
        file.createNewFile();
        EditItem edit = new EditItem();
        EditList load = new EditList();
        String[] properties1 = new String[3];
        String[] properties2 = new String[3];
        properties1[0] = String.valueOf(15);
        properties2[0] = String.valueOf(115);
        properties1[1] = "ZZZZZZZZZZ";
        properties2[1] = "ZZZZZZZZZZ";
        properties1[2] = "test";
        properties2[2] = "testing";
        ItemObject testItem1 = new ItemObject(15, "ZZZZZZZZZZ", "test");
        ItemObject testItem2 = new ItemObject(15, "ZZZZZZZZZZ", "testing");

        edit.addItem("TestIncomplete.txt", properties1);
        edit.addItem("TestIncomplete.txt", properties2);

        ArrayList<ItemObject> actualItems = load.searchName(file, "test");
        Assertions.assertEquals(true, edit.matches(testItem1, actualItems.get(0)));
        Assertions.assertEquals(false, edit.matches(testItem2, actualItems.get(0)));
        file.delete();

    }



}