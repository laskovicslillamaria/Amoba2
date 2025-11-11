package Amoba.Service;

import Amoba.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    private GameService service;
    private Board board;

    @BeforeEach
    void setUp() {
        service = new GameService();
        board = new Board();
    }

    // --- BOARD TESZTEK ---

    @Test
    void testUresTablaInicializalas() {
        int[][] tabla = board.getTabla();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(0, tabla[i][j], "A mezőnek üresnek kell lennie (0)");
            }
        }
    }

    @Test
    void testLepesElfogadas() {
        boolean siker = board.lep(2, 2, 1);
        assertTrue(siker, "A lépésnek sikeresnek kell lennie");
        assertEquals(1, board.getTabla()[2][2]);
    }

    @Test
    void testLepesHibasPozicio() {
        board.lep(0, 0, 1);
        boolean masodikLepes = board.lep(0, 0, 2);
        assertFalse(masodikLepes, "Nem lehet már foglalt mezőre lépni");
    }

    // --- GAME SERVICE TESZTEK ---

    @Test
    void testNyeresSorban() {
        for (int j = 0; j < 5; j++) {
            board.lep(0, j, 1);
        }
        int eredmeny = service.nyertesVizsgalat(board);
        assertEquals(1, eredmeny, "Az 1-es játékosnak nyernie kell sorban");
    }

    @Test
    void testNyeresOszlopban() {
        for (int i = 0; i < 5; i++) {
            board.lep(i, 2, 2);
        }
        int eredmeny = service.nyertesVizsgalat(board);
        assertEquals(2, eredmeny, "A 2-es játékosnak nyernie kell oszlopban");
    }

    @Test
    void testNyeresAtloBalrolJobbra() {
        for (int i = 0; i < 5; i++) {
            board.lep(i, i, 1);
        }
        int eredmeny = service.nyertesVizsgalat(board);
        assertEquals(1, eredmeny, "Az 1-es játékosnak nyernie kell az átlóban");
    }

    @Test
    void testNyeresAtloJobbrolBalra() {
        for (int i = 0; i < 5; i++) {
            board.lep(i, 4 - i, 2);
        }
        int eredmeny = service.nyertesVizsgalat(board);
        assertEquals(2, eredmeny, "A 2-es játékosnak nyernie kell a másik átlóban");
    }

    @Test
    void testNincsNyertes() {
        board.lep(0, 0, 1);
        board.lep(1, 1, 2);
        board.lep(2, 2, 1);
        int eredmeny = service.nyertesVizsgalat(board);
        assertEquals(0, eredmeny, "Nem szabad nyertest találni");
    }

    @Test
    void testKovetkezoJatekos() {
        assertEquals(2, service.kovetkezoJatekos(1));
        assertEquals(1, service.kovetkezoJatekos(2));
    }

    @Test
    void testAiLepUresHelyre() {
        // Minden mező üres, az AI-nak érvényes mezőt kell választania
        service.aiLep(board);
        boolean vanO = false;
        int[][] t = board.getTabla();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (t[i][j] == 2) {
                    vanO = true;
                    break;
                }
            }
        }
        assertTrue(vanO, "Az AI-nak lépnie kell egy üres mezőre");
    }
}