package Amoba.Ai;

import Amoba.Board;
import Amoba.Service.GameService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AiLepesTest {

    // --- AI LÉPÉS TESZT ---

    @Test
    void testAiLepUresHelyre() {
        GameService service = new GameService("AItest");
        Board board = new Board();
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