package lightfilesystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.tinylog.Logger;

/**
 * Maga a fájlrendszer, ami a felhasználókat, csoportokat és a fájlokat
 * rendezi, irányítja és tartja számon.
 * A jelenlegi szabvány szerint, a rendszerünkben minden a neve alapján
 * van azonosítva, ezért minden név egyedi.
 */
@Data
public class FileSystem {
    List<File> files = new ArrayList<File>();
    List<Group> groups = new ArrayList<Group>();
    List<User> users = new ArrayList<User>();

    @Override
    public String toString(){
        return String.format(
                    "FileSystem(#OfFiles=%d, #OfUsers=%d, #OfGroups=%d)",
                    this.files.size(),
                    this.users.size(),
                    this.groups.size()
                );
    }

    /**
     * Létrehoz egy fájlt a rendszerünkben és eltárolja azt.
     *
     * @param title A fájl neve.
     * @return Létrehozott fájl.
     */
    @JsonIgnore
    public File createFile(String title, User author){
        Logger.info(String.format("[FILESYSTEM] Creating file: %s by %s", title, author.getName()));
        File file = File.createFile(title, author);
        this.files.add(file);
        return file;
    }

    /**
     * Létrehoz egy csoportot a rendszerünkben és eltárolja azt.
     *
     * @param name A csoport neve.
     * @return Létrehozott csoport.
     */
    @JsonIgnore
    public Group createGroup(String name){
        Logger.info(String.format("[FILESYSTEM] Creating group: %s", name));
        Group group = Group.createGroup(name);
        this.groups.add(group);
        return group;
    }

    /**
     * Létrehoz egy felhasználót a rendszerünkben és eltárolja azt.
     *
     * @param name A felhasználó neve.
     * @return Létrehozott felhasználó.
     */
    @JsonIgnore
    public User createUser(String name){
        Logger.info(String.format("[FILESYSTEM] Creating user: %s", name));
        User user = User.createUser(name);
        this.users.add(user);
        return user;
    }

    /**
     * Vizsgálja, hogy létre tudunk-e hozni ilyen nevű felhasználót.
     *
     * @param name A felhasználó neve.
     * @return Igaz ha még nem létezik ilyen nevű felhasználó.
     */
    @JsonIgnore
    public boolean canCreateUser(String name){
        return this.users.stream().noneMatch(user -> user.getName().equals(name));
    }

    /**
     * Vizsgálja, hogy létre tudunk-e hozni ilyen nevű csoportot.
     *
     * @param name A csoport neve.
     * @return Igaz ha még nem létezik ilyen nevű csoport.
     */
    @JsonIgnore
    public boolean canCreateGroup(String name){
        return this.groups.stream().noneMatch(group -> group.getName().equals(name));
    }

    /**
     * Vizsgálja, hogy létre tudunk-e hozni ilyen nevű fájlt.
     *
     * @param title A fájl neve.
     * @return Igaz ha még nem létezik ilyen nevű fájl.
     */
    @JsonIgnore
    public boolean canCreateFile(String title){
        return this.files.stream().noneMatch(file -> file.getTitle().equals(title));
    }

    /**
     * Visszaadja a kért felhasználót a neve alapján.
     *
     * @param name A felhasználó neve.
     * @return Kért felhasználó.
     */
    @JsonIgnore
    public Optional<User> getUser(String name){
        return this.users.stream().filter(user -> user.getName().equals(name)).findFirst();
    }

    /**
     * Visszaadja a kért fájlt a neve alapján.
     *
     * @param title A fájl neve.
     * @return Kért fájl.
     */
    @JsonIgnore
    public Optional<File> getFile(String title){
        return this.files.stream().filter(file -> file.getTitle().equals(title)).findFirst();
    }
}
