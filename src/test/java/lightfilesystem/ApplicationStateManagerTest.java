package lightfilesystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class ApplicationStateManagerTest {

    ApplicationStateManager asm;

    @BeforeEach
    public void setup(){
        asm = new ApplicationStateManager();
        var user = new User("Zsolt");
        var file = new File("file1", user);

        asm.setCurrentUser(Optional.of(user));
        asm.setCurrentFile(Optional.of(file));
    }

    @Test
    @DisplayName("Testing current values")
    public void testCurrentValues(){
        var user = new User("Zsolt");
        Assertions.assertEquals(user, asm.getCurrentUser().get());
        Assertions.assertEquals(new File("file1", user), asm.getCurrentFile().get());
    }

    @Test
    @DisplayName("Testing clearing values")
    public void testClearingValues(){
        asm.setCurrentFile(Optional.empty());
        asm.setCurrentUser(Optional.empty());
        Assertions.assertFalse(asm.getCurrentFile().isPresent());
        Assertions.assertFalse(asm.getCurrentUser().isPresent());
    }
}
