package lightfilesystem;
import org.tinylog.Logger;

public class LightFileSystem {
    public static void main(String[] args) {
        System.out.println("Hello world!!!");
        Logger.info("We are running boys!!!!");

        /*
        User zsolt = new User("Zsolt");
        User jani = new User("Jani");

        Group coolGyus = new Group("Cool Gyus");

        File file = new File("My Lfie", zsolt);
        File file2 = new File("Secret", jani);

        zsolt.setGroup(coolGyus);

        file2.addGroup(coolGyus);
        file2.getAccessChecker().setGroupsAccess(Access.READ);

        Access access = file2.getAccessChecker().getUserAccess(zsolt);
        System.out.println(access);

         */


        /*
        Access access = file.getAccessChecker().getUserAccess(zsolt);

        System.out.println(access);

        System.out.println("Is protected: " + file.getIsProtected());
        file.setPassword("asd");
        System.out.println("Is protected: " + file.getIsProtected());

         */
    }
}
