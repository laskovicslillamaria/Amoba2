package Amoba;

import Amoba.Service.GameService;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Board board = new Board();
        GameService service = new GameService();

        // Név bekérés
        System.out.println("Add meg a nevedet: ");
        String playername = sc.nextLine();

        int aktualisJatekos = 1; // mindig az ember kezd
        int eredmeny = 0;
        int lepesSzam = 0;

        System.out.println("Amőba játék - ember vs gép!");
        System.out.println("==========================");

        while (eredmeny == 0 && lepesSzam < 25) {
            board.kirajzol();

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

        board.kirajzol();
        service.nyertesKiiras(eredmeny, playername);
    }
}