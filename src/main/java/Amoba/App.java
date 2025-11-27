package Amoba;

import Amoba.Model.GameState;
import Amoba.Service.GameService;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        final Board board = new Board();


        // Név bekérés
        System.out.println("Add meg a nevedet: ");
        String playername = sc.nextLine();
        final GameService service = new GameService(playername);

        int aktualisJatekos = 1; // mindig az ember kezd
        int eredmeny = 0;
        int lepesSzam = 0;

        System.out.println("Amőba játék - ember vs gép!");
        System.out.println("==========================");

        while (eredmeny == 0 && lepesSzam < 25) {
            board.kirajzol();
            System.out.println("1: Lépés, 2: Játék mentése, 3: Játék Betöltése, 4: Kilépés");
            System.out.println("Választás: ");

            final int valasztas = sc.nextInt();
            sc.nextLine();

            if (valasztas == 2) {
              final  GameState state = new GameState(copyTabla(board.getTabla()), aktualisJatekos, playername, lepesSzam);
                service.jatekMenteseTxt(state, "mentettJatek.txt");
                continue;
            }

            if (valasztas == 3) {
              final  GameState state = service.jatekBetolteseTxt("mentettJatek.txt");
                if (state != null) {
                    board.setTabla(state.getTabla());
                    aktualisJatekos = state.getAktualisJatekos();
                    playername = state.getPlayername();
                    lepesSzam = state.getLepesSzam();
                    service.setPlayername(playername);
                    System.out.println("Játék betöltve: Játékos = " + playername);
                } else {
                    System.out.println("Nem sikerült betölteni a játékot.");
                }
                continue;
            }
                if (valasztas == 4) {
                    System.out.println("Kilépés...");
                    System.exit(0);
                }


            if (valasztas == 1) {
                if (aktualisJatekos == 1) {
                    // Ember lép
                    System.out.println(playername + " következik (X).");
                    int sor, oszlop;
                    while (true) {
                        System.out.print("Add meg a sort (0-4): ");
                        sor = sc.nextInt();
                        System.out.print("Add meg az oszlopot (0-4): ");
                        oszlop = sc.nextInt();
                        if (board.lep(sor, oszlop, 1)) break;
                        System.out.println("Érvénytelen mező, próbáld újra!");
                    }
                } else {
                    // AI lép
                    System.out.println("Ai következik (O).");
                    service.aiLep(board);
                }

                lepesSzam++;
                eredmeny = service.nyertesVizsgalat(board);
                if (eredmeny == 0) {
                    aktualisJatekos = service.kovetkezoJatekos(aktualisJatekos);
                }

            }
        }
            board.kirajzol();
            service.nyertesKiiras(eredmeny, playername);

            service.statMentes(eredmeny, playername);
            service.statKiolvasas();



    }

    private static int[][] copyTabla(int[][] src) {
      final int [][] dst = new int[src.length][src[0].length];
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dst[i], 0, src[i].length);
        }
        return dst;
    }

}
