package lightfilesystem;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public boolean getIsProtected(){
        return this.password.isPresent();
    }

    public void setPassword(String password){
        this.password = Optional.of(password);
    }

    public void joinGroup(Group group){
        this.groups.add(group);
    }
}
