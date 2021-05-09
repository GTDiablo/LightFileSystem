package lightfilesystem;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileSystem {
    List<File> files = new ArrayList<File>();
    List<Group> groups = new ArrayList<Group>();
    List<User> users = new ArrayList<User>();

    @Override
    public String toString(){
        return String.format(
                    "FileSystem(#OfFiles=%d, #OfUsers=%d, #OfGroups=%d)",
                    this.files.size(),
                    this.users.size(),
                    this.groups.size()
                );
    }

    public File createFile(String title, User author){
        File file = File.createFile(title, author);
        this.files.add(file);
        return file;
    }

    public Group createGroup(String name){
        Group group = Group.createGroup(name);
        this.groups.add(group);
        return group;
    }

    public User createUser(String name){
        User user = User.createUser(name);
        this.users.add(user);
        return user;
    }

    // TODO
    public boolean canCreateUser(String name){
        return this.users.stream().noneMatch(user -> user.getName().equals(name));
    }

    // TODO
    public boolean canCreateGroup(String name){
        return this.groups.stream().noneMatch(group -> group.getName().equals(name));
    }

    // TODO
    public boolean canCreateFile(String title){
        return this.files.stream().noneMatch(file -> file.getTitle().equals(title));
    }
}
