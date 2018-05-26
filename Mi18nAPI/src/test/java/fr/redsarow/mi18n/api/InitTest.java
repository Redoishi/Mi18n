package fr.redsarow.mi18n.api;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * @author redsarow
 * @since 1.0.0
 */
public abstract class InitTest {

    public static void init(){
        Mi18nAPI.setLOGGER(Logger.getLogger(I18nTest.class.getName()));
        Mi18nAPI.DEFAULT_SERVER_LOCAL = Locale.ENGLISH;
        Mi18nAPI.setLanguageBundle(ResourceBundle.getBundle("Mi18nAPI_language", Mi18nAPI.DEFAULT_SERVER_LOCAL));
        Mi18nAPI.setMessageFormat(new MessageFormat(""));
        Mi18nAPI.getMessageFormat().setLocale(Mi18nAPI.DEFAULT_SERVER_LOCAL);
    }
}
