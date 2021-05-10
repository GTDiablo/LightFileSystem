package lightfilesystem;

import lombok.Data;
/**
 * Felhasználó osztály a felhasználó adatainak tárolására.
 */
@Data
public class User {
    String name;
    Group group;

    public User(){}
    public User(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format("User(%s)", this.name);
    }

    public boolean equals(User other){
        return this.name.equals(other.name);
    }

    /**
     * Visszaad egy User osztály instance-t.
     *
     * @param name A felhasználó neve.
     * @return Felhasználó instance.
     */
    public static User createUser(String name){
        return new User(name);
    }
}
