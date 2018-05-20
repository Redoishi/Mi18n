package fr.redsarow.mi18nAPI;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class Mi18nAPI extends JavaPlugin {

    public static Logger LOGGER;
    public static Locale DEFAULT_SERVER_LOCAL = Locale.ENGLISH;

    @Override
    public void onEnable() {
        try{
            LOGGER=getLogger();
        } catch (Exception e) {
            getLogger().severe(e.getLocalizedMessage());
            e.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {

    }
}
