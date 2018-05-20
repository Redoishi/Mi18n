package fr.redsarow.mi18nAPI;

import fr.redsarow.mi18nAPI.save.ISavePlayerParam;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class PlayerParamMng {

    private HashMap<UUID, Locale> playerLocal;
    private ISavePlayerParam iSavePlayerParam;

    public PlayerParamMng(ISavePlayerParam iSavePlayerParam) {
        this.playerLocal = new HashMap<>();
        this.iSavePlayerParam = iSavePlayerParam;
    }

    public Locale getPlayerLocal(Player player) {
        UUID uniqueId = player.getUniqueId();
        if (playerLocal.containsKey(uniqueId)) {
            return playerLocal.get(uniqueId);
        }
        Locale localForPlayer = iSavePlayerParam.getLocalForPlayer(player);
        playerLocal.put(uniqueId, localForPlayer);
        return localForPlayer;
    }
}
