package lightfilesystem;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Fájl osztály a fájl adatainak tárolására.
 */
@Data
public class File {
    String title;
    Date created;
    Date updated;
    String content;
    User author;
    List<Group> groups;
    Optional<String> password = Optional.empty();
    Access groupsAccess = Access.NONE;
    Access otherAccess = Access.NONE;

    @Override
    public String toString(){
        return String.format("File(title=%s, author=%s)", this.title, this.author.getName());
    }

    public File(String title, User author) {
        this.title = title;
        this.author = author;
        this.created = new Date();
        this.updated = new Date();
        this.groups = new ArrayList<Group>();
    }

    /**
     * Vizsgálja, hogy a fájl jelszóval védett-e.
     *
     * @return Igaz, ha a fájlhoz jelszó lett adva.
     */
    public boolean getIsProtected(){
        return this.password.isPresent();
    }

    /**
     * A fájlt le tudjuk védeni egy jelszóval.
     *
     * @param password A jelszó a fájlhoz.
     */
    public void setPassword(String password){
        this.password = Optional.of(password);
    }

    /**
     * A megadott csoportok tulajdonába kerül a fájl.
     *
     * @param group Csoport object
     */
    public void addGroup(Group group){
        this.groups.add(group);
    }

    public static File createFile(String title, User author){
        return new File(title, author);
    }
}
