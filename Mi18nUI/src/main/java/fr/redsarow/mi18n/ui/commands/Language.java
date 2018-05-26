package fr.redsarow.mi18n.ui.commands;

import fr.redsarow.mi18n.ui.Mi18nUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class Language extends AMyCommand {

    public Language(Mi18nUI mi18nUI, CommandMap commandMap) {
        super(mi18nUI, "language");
        addDescription("set language. no value => reset");
        addUsage("/language [language] [country]");
        addAliases("l");

        addListTabbComplete(0, "fr", "en");
        addListTabbComplete(1, "FR", "EN");

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + Mi18nUI.getI18n().get("cmd.language.error.noPlayer"));
            return true;
        }
        Player player = (Player) sender;
        if(args.length<=0){
            boolean b = Mi18nUI.getPlayerParamMng().rmPlayerLocal(player);//
            if(b){
                Locale playerLocal = Mi18nUI.getPlayerParamMng().getPlayerLocal(player);
                player.sendMessage(Mi18nUI.getI18n().get(player, "cmd.language.setLang",
                        ChatColor.DARK_AQUA+playerLocal.getLanguage()+"-"+playerLocal.getCountry()));
            }else{
                player.sendMessage(ChatColor.RED+ Mi18nUI.getI18n().get(player, "cmd.language.error.setLang"));
            }
        }else{
            String language=args[0].toLowerCase();
            String country="";
            if(args.length>=2){
                country = args[1].toUpperCase();
            }
            Locale locale = new Locale(language, country);
            boolean b = Mi18nUI.getPlayerParamMng().setPlayerLocal(player, locale);//
            if(b){
                Locale playerLocal = Mi18nUI.getPlayerParamMng().getPlayerLocal(player);
                player.sendMessage(Mi18nUI.getI18n().get(player, "cmd.language.setLang",
                        ChatColor.DARK_AQUA+playerLocal.getLanguage()+"-"+playerLocal.getCountry()));
            }else{
                player.sendMessage(ChatColor.RED+ Mi18nUI.getI18n().get(player, "cmd.language.error.setLang"));
            }
        }
        return true;
    }
}
