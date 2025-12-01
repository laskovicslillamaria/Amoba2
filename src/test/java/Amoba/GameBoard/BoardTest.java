package Amoba.GameBoard;

import Amoba.Board;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    // --- BOARD TESZTEK ---

    @Test
    void testUresTablaInicializalas() {
        Board board = new Board();
        int[][] tabla = board.getTabla();

        for (int[] sor : tabla) {
            for (int cella : sor) {
                assertEquals(0, cella, "Minden mezőnek üresnek kell lennie (0)");
            }
        }
    }

    @Test
    void testSikeresLepes() {
        Board board = new Board();
        assertTrue(board.lep(2, 3, 1));
        assertEquals(1, board.getTabla()[2][3]);
    }

    @Test
    void testFoglaltMezoreLepes() {
        Board board = new Board();
        board.lep(1, 1, 1);
        assertFalse(board.lep(1, 1, 2), "Nem szabad foglalt mezőre lépni");
    }

    @Test
    void testUresMezoVizsgalat() {
        Board board = new Board();
        assertTrue(board.ures(2, 2));
        board.lep(2, 2, 1);
        assertFalse(board.ures(2, 2));
    }
    @Test
    void testKirajzol() {
        // Output elfogása
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // GIVEN
        final Board board = new Board();

        // WHEN
        board.kirajzol();

        // THEN
        final String text = output.toString();

        // Ellenőrizzük, hogy a keret megjelent
        assertTrue(text.contains("+-+-+-+-+-+"),
                "A kirajzolásnak tartalmaznia kell a táblavonalakat ( +-+-+-+-+ ).");

        // Ellenőrizzük, hogy az üres mezők (" |") megjelennek
        assertTrue(text.contains(" |"),
                "Az üres mezőknek meg kell jelenniük.");

        // Ellenőrizzük, hogy pontosan 5 sorban jelenik meg mező
        final long pipes = text.lines().filter(line -> line.startsWith("|")).count();
        assertEquals(5, pipes, "Öt sor mezőnek kell megjelennie a kirajzolásban.");

        // System.out visszaállítása
        System.setOut(System.out);
    }
}