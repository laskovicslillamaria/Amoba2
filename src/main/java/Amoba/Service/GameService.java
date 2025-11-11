package Amoba.Service;

import Amoba.Board;
import java.util.Random;

public class GameService {

    // Ellenőrzi, hogy van-e győztes
    public int nyertesVizsgalat(Board board) {
        int[][] t = board.getTabla();

        // Sorok és oszlopok
        for (int i = 0; i < 5; i++) {
            if (t[i][0] != 0 && t[i][0] == t[i][1] && t[i][1] == t[i][2] && t[i][2] == t[i][3] && t[i][3] == t[i][4])
                return t[i][0];
            if (t[0][i] != 0 && t[0][i] == t[1][i] && t[1][i] == t[2][i] && t[2][i] == t[3][i] && t[3][i] == t[4][i])
                return t[0][i];
        }

        // Átlók
        if (t[0][0] != 0 && t[0][0] == t[1][1] && t[1][1] == t[2][2] && t[2][2] == t[3][3] && t[3][3] == t[4][4])
            return t[0][0];
        if (t[0][4] != 0 && t[0][4] == t[1][3] && t[1][3] == t[2][2] && t[2][2] == t[3][1] && t[3][1] == t[4][0])
            return t[0][4];

        return 0; // nincs nyertes
    }

    // Egyszerű véletlen AI – random üres helyre lép
    public void aiLep(Board board) {
        Random rand = new Random();
        int sor, oszlop;
        do {
            sor = rand.nextInt(5);
            oszlop = rand.nextInt(5);
        } while (!board.ures(sor, oszlop));

        board.lep(sor, oszlop, 2);
        System.out.println("A gép (O) a következő helyre lépett: [" + sor + "," + oszlop + "]");
    }

    // Következő játékos meghatározása
    public int kovetkezoJatekos(int aktualis) {
        return (aktualis == 1) ? 2 : 1;
    }

    // Győztes kiírása
    public void nyertesKiiras(int eredmeny) {
        if (eredmeny == 1)
            System.out.println("Az 1-es (X) játékos győzött!");
        else if (eredmeny == 2)
            System.out.println("A gép (O) győzött!");
        else
            System.out.println("Döntetlen!");
    }
}