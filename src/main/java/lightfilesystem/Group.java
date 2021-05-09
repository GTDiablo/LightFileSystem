package lightfilesystem;

import lombok.Data;

/**
 * Csoport osztály a csoport adatainak tárolására.
 */
@Data
public class Group {
    String name;

    public Group(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format("Group(name=%s)", this.name);
    }

    /**
     * Visszaad egy Group osztály instance-t.
     *
     * @param name A csoport neve.
     * @return Csoport instance.
     */
    public static Group createGroup(String name){
        return new Group(name);
    }
}
