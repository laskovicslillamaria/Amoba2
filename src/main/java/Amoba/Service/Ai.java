package Amoba.Service;
import Amoba.Board;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Ai {
    private final GameService service;
    private static final Logger log = LoggerFactory.getLogger(Ai.class);

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
                        log.info("AI (O) lépett a következő mezőre: [{},{}]", sor, oszlop);
//                        System.out.println("AI (O) lépett a következő mezőre: [" + sor + "," + oszlop + "]");
                        return;
                    }
                    board.getTabla()[sor][oszlop] = 0;
                }
            }
        }

        // 2) AI blokkolja a játékost
//        for (int sor = 0; sor < Board.BOARD_SIZE; sor++) {
//            for (int oszlop = 0; oszlop < Board.BOARD_SIZE; oszlop++) {
//                if (board.getTabla()[sor][oszlop] == 0) {
//                    board.getTabla()[sor][oszlop] = 1;
//                    if (service.nyertesVizsgalat(board) == 1) {
//                        board.getTabla()[sor][oszlop] = 0;
//                        board.lep(sor, oszlop, 2);
//                        log.info("AI (O) blokkoló mezőre lépett: [{},{}]", sor, oszlop);
////                        System.out.println("AI (O) lépett a következő mezőre: [" + sor + "," + oszlop + "]");
//                        return;
//                    }
//                    board.getTabla()[sor][oszlop] = 0;
//                }
//            }
//        }
        // 3) különben random lép
        log.info("AI nem talált nyerő vagy blokkoló mezőt -> random lépés");
        superRandom(board);
    }

    private void superRandom(Board board) {
        final Random rnd = new Random();
        while (true) {
            final int sor = rnd.nextInt((Board.BOARD_SIZE));
            final int oszlop = rnd.nextInt((Board.BOARD_SIZE));
            if (board.lep(sor, oszlop, 2)) {
                log.info("AI random mezőre lépett: [{},{}]", sor, oszlop);
//                System.out.println("AI (O) lépett a következő mezőre: [" + sor + "," + oszlop + "]");
                return;
            }
        }
    }
}
