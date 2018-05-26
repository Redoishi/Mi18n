package fr.redsarow.mi18n.api;

import fr.redsarow.mi18n.api.save.ISavePlayerParam;
import org.bukkit.entity.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author redsarow
 * @see PlayerParamMng
 * @since 1.0.0
 */
public class PlayerParamMngTest {

    private final static Player Player1 = mock(Player.class);
    private final static Player Player2 = mock(Player.class);

    private PlayerParamMng instance;

    @Before
    public void setUp() throws Exception {

        when(Player1.getUniqueId()).thenReturn(UUID.randomUUID());
        when(Player2.getUniqueId()).thenReturn(UUID.randomUUID());

        ISavePlayerParam mockISavePlayerParam = mock(ISavePlayerParam.class);
        when(mockISavePlayerParam.getLocalForPlayer(Player1)).thenReturn(Locale.FRENCH);
        when(mockISavePlayerParam.getLocalForPlayer(Player2)).thenReturn(Locale.ENGLISH);
        when(mockISavePlayerParam.saveLocalForPlayer(eq(Player1), notNull())).thenReturn(true);
        when(mockISavePlayerParam.saveLocalForPlayer(eq(Player2), any(Locale.class))).thenReturn(false);
        when(mockISavePlayerParam.rmLocalForPlayer(Player1)).thenReturn(true);
        when(mockISavePlayerParam.rmLocalForPlayer(Player2)).thenReturn(false);

        InitTest.init();
        PlayerParamMng.initInstance();
        instance = PlayerParamMng.getInstance();
        instance.setiSavePlayerParam(mockISavePlayerParam);
    }

    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    @Test
    public void testInit() {
        assertNotNull("PlayerParamMng null", instance);
    }


    @Test
    public void getPlayerLocal() {
        assertEquals("test locale = fr", Locale.FRENCH, instance.getPlayerLocal(Player1));
        assertEquals("test2 locale = fr", Locale.FRENCH, instance.getPlayerLocal(Player1));
        assertEquals("test locale = en", Locale.ENGLISH, instance.getPlayerLocal(Player2));
    }

    @Test
    public void setPlayerLocalOk() {
        Locale newLocal = Locale.JAPANESE;
        assertTrue("test set new local", instance.setPlayerLocal(Player1, newLocal));
        assertEquals("test set new local ok", newLocal, instance.getPlayerLocal(Player1));
    }

    @Test
    public void setPlayerLocalKo() {
        Locale newLocal = Locale.JAPANESE;
        assertFalse("test set new local", instance.setPlayerLocal(Player2, newLocal));
        assertEquals("test set new local ko", Locale.ENGLISH, instance.getPlayerLocal(Player2));
    }

    @Test
    public void rmPlayerLocalOk() {
        assertTrue("test rm local", instance.rmPlayerLocal(Player1));
        assertEquals("test rm local ok", Locale.ENGLISH, instance.getPlayerLocal(Player1));
        //ENGLISH => default server locale
    }

    @Test
    public void rmPlayerLocalKo() {
        assertFalse("test rm local", instance.rmPlayerLocal(Player2));
        assertEquals("test rm local ko", Locale.ENGLISH, instance.getPlayerLocal(Player2));
    }

    @Test
    public void rmPlayer() {
        assertFalse("test rm player", instance.rmPlayer(Player1));
        assertEquals("test rm local ok", Locale.FRENCH, instance.getPlayerLocal(Player1));
        assertTrue("test rm player", instance.rmPlayer(Player1));
        assertEquals("test rm local ok", Locale.FRENCH, instance.getPlayerLocal(Player1));

    }
}