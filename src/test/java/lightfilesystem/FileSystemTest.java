package lightfilesystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileSystemTest {

    FileSystem fileSystem;

    @BeforeEach
    public void setup() {
        this.fileSystem = new FileSystem();
        var zsolt = fileSystem.createUser("Zsolt");
        var jani = fileSystem.createUser("Jani");

        fileSystem.createGroup("g1");
        fileSystem.createGroup("g2");

        fileSystem.createFile("file1", zsolt);
        fileSystem.createFile("file2", jani);
    }

    @Test
    @DisplayName("Testing user creation")
    public void testUserCreation(){
        Assertions.assertFalse(fileSystem.canCreateUser("Zsolt"));
        Assertions.assertFalse(fileSystem.canCreateUser("Jani"));
        Assertions.assertTrue(fileSystem.canCreateUser("Niki"));
        Assertions.assertTrue(fileSystem.canCreateUser("Edit"));
    }

    @Test
    @DisplayName("Testing group creation")
    public void testGroupCreation(){
        Assertions.assertFalse(fileSystem.canCreateGroup("g1"));
        Assertions.assertFalse(fileSystem.canCreateGroup("g2"));
        Assertions.assertTrue(fileSystem.canCreateGroup("g3"));
        Assertions.assertTrue(fileSystem.canCreateGroup("g4"));
    }

    @Test
    @DisplayName("Testing file creation")
    public void testFileCreation(){
        Assertions.assertFalse(fileSystem.canCreateFile("file1"));
        Assertions.assertFalse(fileSystem.canCreateFile("file2"));
        Assertions.assertTrue(fileSystem.canCreateFile("file3"));
        Assertions.assertTrue(fileSystem.canCreateFile("file4"));
    }

    @Test
    @DisplayName("Testing filesystem content")
    public void testFilesystemContent(){
        Assertions.assertEquals(2, fileSystem.getFiles().size());
        Assertions.assertEquals(2, fileSystem.getGroups().size());
        Assertions.assertEquals(2, fileSystem.getFiles().size());
    }
}
