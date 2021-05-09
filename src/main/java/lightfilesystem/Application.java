package lightfilesystem;

import lombok.Data;
import org.tinylog.Logger;

@Data
public class Application {
    FileSystem filesystem;
    ApplicationStateManager stateManager;

    public final String CONFIG_FILE_NAME = "filesystem.json";

    public String getConfigFilePaht(){
        return "ASD";
    }

    public boolean isConfigFileExists(){
        return true;
    }

    public void loadConfigFile(){
        Logger.info("[APPLICATION] Trying to load config file...");

    }

    public void saveConfigFile(){
        Logger.info("[APPLICATION] Saving config file.");
    }
}
