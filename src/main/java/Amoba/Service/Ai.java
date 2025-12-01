package Amoba.Service;
import Amoba.Board;

import java.util.Random;

public class Ai {
    private final GameService service;

    public Ai(GameService service) {
        this.service = service;
    }
    // Ai lép
    public void aiLep(Board board) {
        // 1) AI megnézi, hogy tud-e nyerni közvetlenül
        for (int sor = 0; sor < Board.BOARD_SIZE; sor++) {
            for (int oszlop = 0; oszlop < Board.BOARD_SIZE; oszlop++) {
                if (board.getTabla()[sor][oszlop] == 0) {
                    board.getTabla()[sor][oszlop] = 2;
                    if (service.nyertesVizsgalat(board) == 2) {
                        board.getTabla()[sor][oszlop] = 0;
                        board.lep(sor, oszlop, 2);
                        System.out.println("AI (O) lépett a következő mezőre: [" + sor + "," + oszlop + "]");
                        return;
                    }
                    board.getTabla()[sor][oszlop] = 0;
                }
            }
        }

        // 2) AI blokkolja a játékost
        for (int sor = 0; sor < Board.BOARD_SIZE; sor++) {
            for (int oszlop = 0; oszlop < Board.BOARD_SIZE; oszlop++) {
                if (board.getTabla()[sor][oszlop] == 0) {
                    board.getTabla()[sor][oszlop] = 1;
                    if (service.nyertesVizsgalat(board) == 1) {
                        board.getTabla()[sor][oszlop] = 0;
                        board.lep(sor, oszlop, 2);
                        System.out.println("AI (O) lépett a következő mezőre: [" + sor + "," + oszlop + "]");
                        return;
                    }
                    board.getTabla()[sor][oszlop] = 0;
                }
            }
        }
        // 3) különben random lép
        superRandom(board);
    }

    private void superRandom(Board board) {
        final Random rnd = new Random();
        while (true) {
            final int sor = rnd.nextInt((Board.BOARD_SIZE));
            final int oszlop = rnd.nextInt((Board.BOARD_SIZE));
            if (board.lep(sor, oszlop, 2)) {
                System.out.println("AI (O) lépett a következő mezőre: [" + sor + "," + oszlop + "]");
                return;
            }
        }
    }
}
