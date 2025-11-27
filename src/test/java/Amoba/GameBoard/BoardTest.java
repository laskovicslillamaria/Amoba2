package Amoba.GameBoard;

import Amoba.Board;
import org.junit.jupiter.api.Test;
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
}