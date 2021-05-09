package lightfilesystem;

import lombok.Data;

import java.util.Optional;

@Data
public class ApplicationStateManager {
    Optional<User> currentUser = Optional.empty();
    Optional<File> currentFile = Optional.empty();
}
