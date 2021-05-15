package GUI;

import javafx.util.StringConverter;
import lightfilesystem.LightFileSystem;
import lightfilesystem.User;

class UserStringConverter extends StringConverter<User> {

    public User fromString(String name) {
        return LightFileSystem.getInstance().getFilesystem().getUser(name).get();
    }

    public String toString(User user) {
        return user.getName();
    }
}
