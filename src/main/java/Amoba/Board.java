package Amoba;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Board {
    public static final int BOARD_SIZE = 5;
    public final int[][] tabla = new int[BOARD_SIZE][BOARD_SIZE];

    public void setMezo(int sor, int oszlop, int ertek) {
        this.tabla[sor][oszlop] = ertek;
    }
    // Tábla kirajzolása
    public void kirajzol() {
        System.out.println("+-+-+-+-+-+");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("|");
            for (int j = 0; j < BOARD_SIZE; j++) {
                final int ertek = tabla[i][j];
                if (ertek == 0)
                    System.out.print(" |");
                else if (ertek == 1)
                    System.out.print("X|");
                else
                    System.out.print("O|");
            }
            System.out.println();
            System.out.println("+-+-+-+-+-+");
        }
    }
    // Ellenőrzi, hogy egy mező üres-e
    public boolean ures(int sor, int oszlop) {
        return tabla[sor][oszlop] == 0;
    }
    // Érvényes lépés beállítása, ha üres a mező
    public boolean lep(int sor, int oszlop, int jatekos) {
        if (sor < 0 || sor >= BOARD_SIZE ||
                oszlop < 0 || oszlop >= BOARD_SIZE ||
                tabla[sor][oszlop] != 0) {
            return false;
        }
        tabla[sor][oszlop] = jatekos;
        return true;
    }


}
