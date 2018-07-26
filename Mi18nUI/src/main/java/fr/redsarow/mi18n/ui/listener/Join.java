package fr.redsarow.mi18n.ui.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class Join  implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.getPlayer().sendMessage(ChatColor.AQUA +"Change language whit: /language [language] [country]");
    }
}
