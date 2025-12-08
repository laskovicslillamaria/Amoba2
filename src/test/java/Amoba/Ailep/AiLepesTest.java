package Amoba.Ailep;

import Amoba.Board;
import Amoba.Service.Ai;
import Amoba.Service.GameService;
import org.junit.jupiter.api.Test;

import static Amoba.Board.BOARD_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class AiLepesMockitoTest {

    @Mock
    Board board;

    @Mock
    GameService service;

    @InjectMocks
    Ai ai;

    @Test
    void testAiMeghivjaALepestValodiBoarddal() {

        GameService service = mock(GameService.class);
        when(service.nyertesVizsgalat(any())).thenReturn(0); // ne legyen nyerés

        Ai ai = new Ai(service);
        Board board = new Board();

        ai.aiLep(board);

        verify(service, atLeastOnce()).nyertesVizsgalat(any());
    }

    @Test
    void testAiLepUresHelyre() {
        GameService service = new GameService("AItest");
        Ai ai = new Ai(service);
        Board board = new Board();
        ai.aiLep(board);
        boolean vanO = false;
        int[][] t = board.getTabla();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (t[i][j] == 2) {
                    vanO = true;
                    break;
                }
            }
        }
        assertTrue(vanO, "Az AI-nak lépnie kell egy üres mezőre");
    }
}