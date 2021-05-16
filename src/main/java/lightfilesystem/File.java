package lightfilesystem;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.tinylog.Logger;

/**
 * Fájl osztály a fájl adatainak tárolására.
 */
@Data
public class File {
    String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    Date created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    Date updated;
    String content;
    User author;
    List<Group> groups;
    String password = "";
    Access groupsAccess = Access.NONE;
    Access otherAccess = Access.NONE;

    @Override
    public String toString(){
        return String.format("File(title=%s, author=%s)", this.title, this.author.getName());
    }
    @JsonIgnore
    public boolean equals(File other){
        return this.title.equals(other.getTitle());
    }

    @Override
    public boolean equals(Object other){
        return other instanceof File && this.title.equals(((File) other).getTitle());
    }

    public File(){}
    public File(String title, User author) {
        this.title = title;
        this.author = author;
        this.created = new Date();
        this.updated = new Date();
        this.groups = new ArrayList<>();
    }

    /**
     * Vizsgálja, hogy a fájl jelszóval védett-e.
     *
     * @return Igaz, ha a fájlhoz jelszó lett adva.
     */
    @JsonIgnore
    public boolean getIsProtected(){
        return !this.password.equals("");
    }

    public void setContent(String content){
        Logger.info(String.format("[APPLICATION] Updating content of file: %s", this.title));
        this.content = content;
        this.updated = new Date();
    }

    /**
     * A megadott csoportok tulajdonába kerül a fájl.
     *
     * @param group Csoport object
     */
    @JsonIgnore
    public void addGroup(Group group){
        this.groups.add(group);
    }

    @JsonIgnore
    public static File createFile(String title, User author){
        return new File(title, author);
    }
}
