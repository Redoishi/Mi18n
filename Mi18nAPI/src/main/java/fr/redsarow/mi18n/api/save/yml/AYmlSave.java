package fr.redsarow.mi18n.api.save.yml;

import fr.redsarow.mi18n.api.Mi18nAPI;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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
            Mi18nAPI.getMessageFormat().applyPattern(Mi18nAPI.getLanguageBundle().getString("init.save.yml.create.folder"));
            Mi18nAPI.getLOGGER().info(Mi18nAPI.getMessageFormat().format(new String[]{dataFolder.getPath()}));
        }

        File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            boolean newFile = file.createNewFile();
            if (!newFile) {
                return false;
            }
            Mi18nAPI.getMessageFormat().applyPattern(Mi18nAPI.getLanguageBundle().getString("init.save.yml.create.file"));
            Mi18nAPI.getLOGGER().info(Mi18nAPI.getMessageFormat().format(new String[]{fileName}));
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
