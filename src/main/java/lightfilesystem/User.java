package lightfilesystem;

import lombok.Data;

import java.util.List;

@Data
public class User {
    String name;
    List<Group> groups;

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
}
