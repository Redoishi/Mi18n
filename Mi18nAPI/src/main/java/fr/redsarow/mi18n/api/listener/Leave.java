package fr.redsarow.mi18n.api.listener;

import fr.redsarow.mi18n.api.Mi18nAPI;
import fr.redsarow.mi18n.api.PlayerParamMng;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class Leave implements Listener {

    Mi18nAPI pl;

    public Leave(Mi18nAPI mi18nAPI) {
        this.pl = mi18nAPI;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        PlayerParamMng.getInstance().rmPlayer(event.getPlayer());
    }
}
