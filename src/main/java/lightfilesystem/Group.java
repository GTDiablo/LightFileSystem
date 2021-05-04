package lightfilesystem;

import lombok.Data;

@Data
public class Group {
    String name;

    public Group(String name){
        this.name = name;
    }
}
