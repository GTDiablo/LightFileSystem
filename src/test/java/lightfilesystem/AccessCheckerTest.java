package lightfilesystem;

import org.junit.jupiter.api.*;

public class AccessCheckerTest {

    User user1;
    User user2;
    File file1;
    File file2;
    Group group1;
    Group group2;


    @BeforeEach
    public void setup(){
        user1 = new User("user1");
        user2 = new User("user2");

        group1 = new Group("g1");
        group2 = new Group("g2");

        user1.setGroup(group1);
        user2.setGroup(group2);

        file1 = new File("file1", user1);
        file2 = new File("file2", user2);

        file2.addGroup(group1);
        file2.addGroup(group2);
    }

    @Test
    @DisplayName("Testing user access")
    public void testUserAccess1(){
        Assertions.assertEquals(Access.ALL, AccessChecker.getUserAccess(file1, user1));
    }

    @Test
    @DisplayName("Testing user access")
    public void testUserAccess2(){
        Assertions.assertEquals(Access.ALL, AccessChecker.getUserAccess(file2, user2));
    }

    @Test
    @DisplayName("Testing user access")
    public void testUserAccess3(){
        Assertions.assertEquals(Access.NONE, AccessChecker.getUserAccess(file1, user2));
    }

    @Test
    @DisplayName("Testing user access")
    public void testUserAccess4(){
        Assertions.assertEquals(Access.NONE, AccessChecker.getUserAccess(file2, user1));
    }

    @Test
    @DisplayName("Testing user access")
    public void testUserAccess5(){
        file2.setGroupsAccess(Access.READ);
        Assertions.assertEquals(Access.READ, AccessChecker.getUserAccess(file2, user1));
    }

    @Test
    @DisplayName("Testing user access")
    public void testUserAccess6(){
        file1.setOtherAccess(Access.WRITE);
        Assertions.assertEquals(Access.WRITE, AccessChecker.getUserAccess(file1, user2));
    }

    @Test
    @DisplayName("Testing user access")
    public void testUserAccess7(){
        file1.setGroupsAccess(Access.READ);
        file1.addGroup(group2);
        Assertions.assertEquals(Access.READ, AccessChecker.getUserAccess(file1, user2));
    }

    @Test
    @DisplayName("Testing password access")
    public void testPasswordAccess(){
        file1.setPassword("test");
        Assertions.assertTrue(AccessChecker.isPasswordValid(file1, "test"));
    }

    @Test
    @DisplayName("Testing password access")
    public void testPasswordAccess2(){
        file1.setPassword("test");
        Assertions.assertFalse(AccessChecker.isPasswordValid(file1, "asd"));
    }
}
