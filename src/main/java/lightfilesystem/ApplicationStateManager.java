package lightfilesystem;

import lombok.Data;

import java.util.Optional;

/**
 * Osztály ami az alkalmazás pillanatnyi állapotát tárolja és vezérli.
 */
@Data
public class ApplicationStateManager {
    Optional<User> currentUser = Optional.empty();
    Optional<File> currentFile = Optional.empty();
}
