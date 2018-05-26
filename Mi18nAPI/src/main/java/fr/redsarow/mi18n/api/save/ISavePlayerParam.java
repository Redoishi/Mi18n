package fr.redsarow.mi18n.api.save;

import org.bukkit.entity.Player;

import java.util.Locale;

/**
 * @author redsarow
 * @since 1.0.0
 */
public interface ISavePlayerParam {

    /**
     * @param player
     *
     * @return {@link Locale} for the player or null if {@link Locale} is not set.
     */
    Locale getLocalForPlayer(Player player);

    /**
     * @param player
     * @param locale
     *
     * @return true if ok, else false;
     */
    boolean saveLocalForPlayer(Player player, Locale locale);


    /**
     * @param player
     *
     * @return true if ok, else false;
     */
    boolean rmLocalForPlayer(Player player);

    void shutdown();
}
