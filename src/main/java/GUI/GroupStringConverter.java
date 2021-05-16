package GUI;

import javafx.util.StringConverter;
import lightfilesystem.Group;
import lightfilesystem.LightFileSystem;

import java.util.Optional;

class GroupStringConverter extends StringConverter<Group> {

    public Group fromString(String name) {
        return LightFileSystem.getInstance().getFilesystem().getGroup(name).get();
    }

    public String toString(Group group) {
        return group != null ? group.getName() : "";
    }
}
