package fr.redsarow.mi18n.api;

import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class I18nTest {

    @Before
    public void setUp() throws Exception {
        Mi18nAPI.setLOGGER(Logger.getLogger(I18nTest.class.getName()));
        Mi18nAPI.DEFAULT_SERVER_LOCAL = Locale.ENGLISH;
        Mi18nAPI.setLanguageBundle(ResourceBundle.getBundle("Mi18nAPI_language", Mi18nAPI.DEFAULT_SERVER_LOCAL));
        Mi18nAPI.setMessageFormat(new MessageFormat(""));
        Mi18nAPI.getMessageFormat().setLocale(Mi18nAPI.DEFAULT_SERVER_LOCAL);
    }

    @Test
    public void testInit() throws Exception {
        String baseName = "test";
        Locale defaultLocal = Locale.ENGLISH;
        List<Locale> localeList = new ArrayList<>();
        localeList.add(Locale.FRANCE);
        localeList.add(Locale.JAPANESE);
        I18n i18n = new I18n(I18nTest.class, baseName, defaultLocal, Locale.FRANCE, Locale.JAPANESE);

        assertEquals("", baseName, i18n.getBaseName());

        assertEquals("get Default Locale", defaultLocal, i18n.getDefaultLocale());

        assertEquals("get Locals", localeList, i18n.getLocales());
    }

    //<editor-fold desc="test get">
    @Test
    public void testGetDefaultPlLocalEqualsServerLocal() throws Exception {
        String baseName = "test";
        Locale defaultLocal = Locale.ENGLISH;
        I18n i18n = new I18n(I18nTest.class, baseName, defaultLocal);

        assertEquals("", "test en", i18n.get("init.test"));
    }

    @Test
    public void testGetDefaultPlLocalNotEqualsServerLocal() throws Exception {
        String baseName = "test";
        Locale defaultLocal = Locale.FRANCE;
        I18n i18n = new I18n(I18nTest.class, baseName, defaultLocal);

        assertEquals("", "test fr", i18n.get("init.test"));
    }
    //</editor-fold>
}