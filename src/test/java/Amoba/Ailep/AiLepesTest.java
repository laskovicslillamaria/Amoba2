package Amoba.Ailep;

import Amoba.Board;
import Amoba.Service.Ai;
import Amoba.Service.GameService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AiLepesTest {

    // --- AI LÉPÉS TESZT ---

    @Test
    void testAiLepUresHelyre() {
        GameService service = new GameService("AItest");
        Ai ai = new Ai(service);
        Board board = new Board();
        ai.aiLep(board);
        boolean vanO = false;
        int[][] t = board.getTabla();
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                if (t[i][j] == 2) {
                    vanO = true;
                    break;
                }
            }
        }
        assertTrue(vanO, "Az AI-nak lépnie kell egy üres mezőre");
    }
}