package fr.redsarow.mi18nAPI.save.yml;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static fr.redsarow.mi18nAPI.Mi18nAPI.*;

/**
 * @author redsarow
 * @since 1.0.0
 */
public abstract class AYmlSave {

    protected static boolean initFile(File dataFolder, String fileName) throws IOException {
        if (!dataFolder.exists()) {
            boolean mkdirs = dataFolder.mkdirs();
            if (!mkdirs) {
                return false;
            }
            MESSAGE_FORMAT.applyPattern(LANGUAGE_BUNDLE.getString("init.save.yml.create.folder"));
            LOGGER.info(MESSAGE_FORMAT.format(new String[]{dataFolder.getPath()}));
        }

        File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            boolean newFile = file.createNewFile();
            if (!newFile) {
                return false;
            }
            MESSAGE_FORMAT.applyPattern(LANGUAGE_BUNDLE.getString("init.save.yml.create.file"));
            LOGGER.info(MESSAGE_FORMAT.format(new String[]{fileName}));
        }
        return true;
    }

    protected static void setSection(YamlConfiguration configFile, String path) {
        if (!configFile.contains(path)) {
            configFile.createSection(path);
        }
    }

    protected static void setSectionVal(YamlConfiguration configFile, String path, Object val) {
        setSection(configFile, path);
        configFile.set(path, val);
    }
}
