package fr.redsarow.mi18n.ui;

import fr.redsarow.mi18n.api.I18n;
import fr.redsarow.mi18n.api.PlayerParamMng;
import fr.redsarow.mi18n.ui.commands.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.logging.Level;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class Mi18nUI extends JavaPlugin {

    private static I18n i18n;
    private static PlayerParamMng playerParamMng;

    @Override
    public void onEnable() {
        try{
            i18n = new I18n(this.getClass(), "language",  Locale.ENGLISH, Locale.FRENCH);
            playerParamMng = PlayerParamMng.getInstance();

            getLogger().info(i18n.get("init.command"));
            Field f = null;
            f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap commandMap = (CommandMap) f.get(Bukkit.getServer());

            new Language(this, commandMap);

        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getLocalizedMessage(), e);
            this.getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {

    }

    public static I18n getI18n() {
        return i18n;
    }

    public static PlayerParamMng getPlayerParamMng() {
        return playerParamMng;
    }
}
