package lightfilesystem;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.Optional;

/**
 * The HelloWorld program implements an application that
 * simply displays "Hello World!" to the standard output.
 *
 * @author  Boda Zsolt
 * @version SNAPSHOT-1
 * @since   2021-05-06
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!!!");
        Logger.info("We are running boys!!!!");


        /*
        try {
            var lfs = LightFileSystem.getInstance();
            lfs.init();
            System.out.println(lfs.getFilesystem());

            var zsolt = lfs.getFilesystem().createUser("Zsolt");

            // LightFileSystem.getInstance().getStateManager().setCurrentUser(Optional.of(zsolt));
            var a = lfs.getFilesystem().createUser("Zsolt");
            var b = lfs.getFilesystem().createUser("Zsolt");
            var c = lfs.getFilesystem().createUser("Zsolt");

            var lfs2 = LightFileSystem.getInstance();
            System.out.println(lfs2.getFilesystem());

            lfs2.saveConfigFile();

            var currentUser = LightFileSystem.getInstance().getStateManager().getCurrentUser();
            System.out.println(currentUser.get());
        } catch (IOException e){
            System.out.println("HIBA");
        }

         */



        /*
        Application app = new Application();

        try {
            app.loadConfigFile();
            System.out.println(app.getFilesystem());
            User asd = app.getFilesystem().createUser("JANIVOKAZTCSOH");
            app.saveConfigFile();

        } catch (IOException e){
            System.out.println("HIBA");
            System.out.println(e);
        }

         */
        /*
        FileSystem fs = new FileSystem();

        User zsolt = fs.createUser("Zsolt");
        File cv = fs.createFile("MyCv.txt", zsolt);

        cv.setContent("Im good, hire me!");

        fs.getFiles().forEach(file -> System.out.println(file.getContent()));

         */

        /*
        ApplicationStateManager asm = new ApplicationStateManager();


        Optional<User> currentUser = asm.getCurrentUser();
        System.out.println(currentUser);
         */

        /*
        FileSystem fs = new FileSystem();
        // System.out.println(fs);
        System.out.println(fs.canCreateUser("Zsolt"));
        User zsolt = fs.createUser("Zsolt");
        //System.out.println(fs);
        //System.out.println(zsolt);
        System.out.println(fs.canCreateUser("Zsolt"));

         */

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
