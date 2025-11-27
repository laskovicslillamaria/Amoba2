package Amoba.Statisztika;

import Amoba.Model.GameState;
import Amoba.Service.GameService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class StatMentTest {

    // --- STATISZTIKA ÉS JÁTÉK MENTÉS ÉS BETÖLTÉS TESZTEK ---

    @Test
    void testStatisztikaMentes() throws IOException {
        GameService service = new GameService("Teszt");

        service.statMentes(1, "Teszt");

        Path file = Paths.get("scores.txt");
        assertTrue(Files.exists(file));

        String tartalom = Files.readString(file);
        assertTrue(tartalom.contains("Teszt"));
    }
    @Test
    void testJatekMenteseEsBetoltese() throws Exception {
        GameService g = new GameService("Marci");

        int[][] tabla = new int[5][5];
        tabla[1][2] = 1;
        tabla[3][3] = 2;

        GameState state = new GameState(tabla, 1, "Marci", 8);

        String filename = "test_save.txt";

        assertTrue(g.jatekMenteseTxt(state, filename));

        GameState loaded = g.jatekBetolteseTxt(filename);
        assertNotNull(loaded);

        assertEquals("Marci", loaded.getPlayername());
        assertEquals(1, loaded.getAktualisJatekos());
        assertEquals(8, loaded.getLepesSzam());
        assertEquals(1, loaded.getTabla()[1][2]);
        assertEquals(2, loaded.getTabla()[3][3]);

        Files.deleteIfExists(Path.of(filename));
    }
}

