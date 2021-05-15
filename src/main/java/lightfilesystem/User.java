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

    @Override
    public boolean equals(Object other){
        if(other instanceof User && this.name.equals(((User) other).getName())){
            return true;
        }
        return false;
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
