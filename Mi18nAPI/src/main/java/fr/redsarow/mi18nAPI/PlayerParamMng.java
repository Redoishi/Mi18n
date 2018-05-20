package fr.redsarow.mi18nAPI;

import fr.redsarow.mi18nAPI.save.ISavePlayerParam;
import fr.redsarow.mi18nAPI.save.SaveFactory;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class PlayerParamMng {

    private static PlayerParamMng ourInstance;

    private HashMap<UUID, Locale> playerLocal;
    private ISavePlayerParam iSavePlayerParam;

    private PlayerParamMng(ISavePlayerParam iSavePlayerParam) {
        this.playerLocal = new HashMap<>();
        this.iSavePlayerParam = iSavePlayerParam;
    }

    public static void initInstance() {
        new PlayerParamMng(SaveFactory.getSavePlayerParam());
    }

    public static PlayerParamMng getInstance() {
        return ourInstance;
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

    public boolean setPlayerLocal(Player player, Locale locale){
        boolean ok = iSavePlayerParam.saveLocalForPlayer(player, locale);
        if(ok){
            playerLocal.put(player.getUniqueId(), locale);
        }
        return ok;
    }

    public boolean rmPlayerLocal(Player player){
        boolean ok = iSavePlayerParam.rmLocalForPlayer(player);
        if(ok){
            playerLocal.remove(player.getUniqueId());
        }
        return ok;
    }
}
