package Amoba;

import Amoba.Model.GameState;
import Amoba.Service.GameService;
import java.util.Scanner;
import Amoba.Service.Ai;
import static Amoba.Board.BOARD_SIZE;
import static java.lang.System.*;

public class App {

    public static void main(String[] args) {

        String playername;
        final GameService service;

        final Board board;
        int eredmeny;
        try (Scanner sc = new Scanner(in)) {
            board = new Board();
            out.println("Add meg a nevedet: ");
            playername = sc.nextLine();
            service = new GameService(playername);
            final Ai ai = new Ai(service);

            int aktualisJatekos = 1;
            eredmeny = 0;
            int lepesSzam = 0;

            out.println("Amőba játék - ember vs gép!");
            out.println("==========================");

            while (eredmeny == 0 && lepesSzam < BOARD_SIZE * BOARD_SIZE) {
                board.kirajzol();
                out.println("1: Lépés, 2: Játék mentése, 3: Játék Betöltése, 4: Kilépés");
                out.print("Választás: ");

                final int valasztas = sc.nextInt();
                sc.nextLine();
                if (valasztas == 2) {
                    final GameState state = new GameState(App.copyTabla(board.getTabla()), aktualisJatekos, playername, lepesSzam);
                    service.jatekMenteseTxt(state, "mentettJatek.txt");
                    continue;
                }
                if (valasztas == 3) {
                    final GameState state = service.jatekBetolteseTxt("mentettJatek.txt");
                    if (state == null) out.println("Nem sikerült betölteni a játékot.");
                    else {
                        for (int i = 0; i < BOARD_SIZE; i++)
                            for (int j = 0; j < BOARD_SIZE; j++) board.setMezo(i, j, state.getTabla()[i][j]);
                        aktualisJatekos = state.getAktualisJatekos();
                        playername = state.getPlayername();
                        lepesSzam = state.getLepesSzam();
                        service.setPlayername(playername);
                        out.println("Játék betöltve: Játékos = " + playername);
                    }
                    continue;
                }
                        if (valasztas == 4) {
                            out.println("Kilépés...");
                            exit(0);
                        }
                        if (valasztas != 1)
                            continue;
                        if (aktualisJatekos != 1) {
                            // AI lép
                            out.println("Ai következik (O).");
                            ai.aiLep(board);
                        } else { //Ember lép
                            out.println(playername + " következik (X).");
                            int sor;
                            do {
                                out.print("Add meg a sort (0-4): ");
                                sor = sc.nextInt();
                                out.print("Add meg az oszlopot (0-4): ");
                                final int oszlop = sc.nextInt();
                                if (board.lep(sor, oszlop, 1)) break;
                                out.println("Érvénytelen mező, próbáld újra!");
                            } while (true);
                        }
                        lepesSzam++;
                        eredmeny = service.nyertesVizsgalat(board);
                        if (eredmeny == 0) aktualisJatekos = service.kovetkezoJatekos(aktualisJatekos);

                        else {
                            final var state = service.jatekBetolteseTxt("mentettJatek.txt");
                            if (state == null) out.println("Nem sikerült betölteni a játékot.");
                            else {
                                for (int j = 0; j < BOARD_SIZE; j++) board.setMezo(0, j, state.getTabla()[0][j]);
                                for (int j = 0; j < BOARD_SIZE; j++) board.setMezo(1, j, state.getTabla()[1][j]);
                                for (int j = 0; j < BOARD_SIZE; j++) board.setMezo(2, j, state.getTabla()[2][j]);
                                for (int j = 0; j < BOARD_SIZE; j++) board.setMezo(3, j, state.getTabla()[3][j]);
                                for (int j = 0; j < BOARD_SIZE; j++) board.setMezo(4, j, state.getTabla()[4][j]);

                            }
                        }
                    }
            board.kirajzol();
            service.nyertesKiiras(eredmeny, playername);

            service.statMentes(eredmeny, playername);
            service.statKiolvasas();

            }
        }
        public static int[][] copyTabla (int[][] src) {
            final int[][] dst = new int[src.length][src[0].length];
            for (int i = 0; i < src.length; i++) arraycopy(src[i], 0, dst[i], 0, src[i].length);
            return dst;
            }

        }