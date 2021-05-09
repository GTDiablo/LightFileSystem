package lightfilesystem;

import lombok.Data;

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

    public static Group createGroup(String name){
        return new Group(name);
    }
}
