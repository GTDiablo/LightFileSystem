package lightfilesystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

public class FileTest {

    File file1;
    File file2;
    User author;

    @BeforeEach
    public void setup(){
        author = new User("Zsolt");
        file1 = new File("README.md", author);
        file2 = new File("README.md", author);
    }

    @Test
    @DisplayName("Testing file")
    public void testFileEquals(){
        file1.setTitle("same");
        file2.setTitle("same");
        Assertions.assertEquals(file1.getTitle(), file2.getTitle());
    }

    @Test
    @DisplayName("Testing file")
    public void testFileContent(){
        file1.setContent("I will get a five");
        Assertions.assertEquals("I will get a five", file1.getContent());
    }

    @Test
    @DisplayName("Testing file protection with password.")
    public void testFileProtected(){
        file1.setPassword("Strong password");
        Assertions.assertTrue(file1.getIsProtected());
    }

    @Test
    @DisplayName("Testing file updated")
    public void testFileUpdated(){
        file1.setContent("Hello there");
        Assertions.assertFalse(file1.getCreated().equals(file1.getUpdated()));
    }
}
