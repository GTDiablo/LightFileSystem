package lightfilesystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.tinylog.Logger;
import java.io.InputStream;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;

/**
 * Maga az alkalmazás modell és vezérlő rétege.
 * Valamint a folyamata irányító eljárásokat idító osztály.
 * A class maga singleton, szóval csak egy példány lehet belőle.
 */
@Data
public class LightFileSystem {
    FileSystem filesystem;
    ApplicationStateManager stateManager;
    private static LightFileSystem instance;
    boolean isInitalized = false;

    /**
     * Konfig fájl neve amiben elmentjük a fylesystem adatait
     */
    public final String CONFIG_FILE_NAME = "filesystem.json";
    /**
     * Object mapper, ami a json írásában és olvasásában segít
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    private LightFileSystem(){}

    /**
     * Singleton design patternhez metódus, ami visszadja az egyetlen példányt.
     *
     * @return Singleton példány.
     */
    public static LightFileSystem getInstance(){
        if(instance == null){
            instance = new LightFileSystem();
        }
        return instance;
    }

    /**
     * Megkeresi a config fájlt
     *
     * @return Fájl útvonala
     */
    public File getConfigFile() throws IOException {
        String homeDir = System.getProperty("user.home");
        return new File( homeDir + File.separator + CONFIG_FILE_NAME);
    }

    /**
     * Leellenőrzni, hogy a config fájl létezik-e.
     *
     * @return Igaz, ha létezik a config fájl.
     */
    public boolean isConfigFileExists() throws IOException {
        return this.getConfigFile().exists();
    }

    /**
     * Betölti a config fájlból az adatokat.
     */
    public void loadConfigFile() throws IOException{
        Logger.info("[APPLICATION] Trying to load config file...");

        if(this.isConfigFileExists()){
            var configFile = this.getConfigFile();
            Logger.info(String.format("[APPLICATION] Loading config from user's config file from %s", configFile.getAbsolutePath()));
            this.filesystem = OBJECT_MAPPER.readValue(configFile, new TypeReference<FileSystem>() {});
        } else {
            Logger.info("[APPLICATION] Loading config from default config file");
            InputStream is = getClass().getResourceAsStream(CONFIG_FILE_NAME);
            this.filesystem = OBJECT_MAPPER.readValue(is, new TypeReference<FileSystem>() {});
        }

    }

    /**
     * Felállítja a rendszert.
     */
    public void init() throws IOException {
        this.loadConfigFile();
        this.stateManager = new ApplicationStateManager();
        this.stateManager.setCurrentUser(this.filesystem.getUsers().stream().findFirst());
        this.isInitalized = true;
    }

    /**
     * Elmenti a config fájlba az adatokat.
     */
    public void saveConfigFile() throws IOException {
        var configFile = this.getConfigFile();
        Logger.info(String.format("[APPLICATION] Saving config file to %s", configFile.getAbsolutePath()));
        // OBJECT_MAPPER.writeValue(configFile, this.filesystem);
    }
}
