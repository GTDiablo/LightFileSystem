package lightfilesystem;
import org.tinylog.Logger;

public class LightFileSystem {
    public static void main(String[] args) {
        System.out.println("Hello world!!!");
        Logger.info("We are running boys!!!!");

        User zsolt = new User("Zsolt");
        System.out.println(zsolt);
        File file = new File("My Lfie", zsolt);
        Access access = file.accessChecker.getUserAccess(zsolt);
        System.out.println(access);
        System.out.println("Is protected: " + file.getIsProtected());
        file.setPassword("asd");
        System.out.println("Is protected: " + file.getIsProtected());
    }
}
