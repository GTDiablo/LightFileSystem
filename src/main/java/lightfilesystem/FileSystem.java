package lightfilesystem;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileSystem {
    List<User> users = new ArrayList<>();
    List<File> files = new ArrayList<>();
    List<Group> groups = new ArrayList<>();
}
