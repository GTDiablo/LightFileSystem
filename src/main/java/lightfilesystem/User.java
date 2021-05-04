package lightfilesystem;

import lombok.Data;

@Data
public class User {
    String name;
    Group group;

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
