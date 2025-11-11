package Amoba;

public class Board {
    private final int[][] tabla = new int[5][5];

    public int[][] getTabla() {
        return tabla;
    }

    // Tábla kirajzolása
    public void kirajzol() {
        System.out.println("+-+-+-+-+-+");
        for (int i = 0; i < 5; i++) {
            System.out.print("|");
            for (int j = 0; j < 5; j++) {
                int ertek = tabla[i][j];
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

    // Érvényes lépés beállítása, ha üres a mező
    public boolean lep(int sor, int oszlop, int jatekos) {
        if (sor < 0 || sor >= 5 || oszlop < 0 || oszlop >= 5 || tabla[sor][oszlop] != 0) {
            return false;
        }
        tabla[sor][oszlop] = jatekos;
        return true;
    }

    // Ellenőrzi, hogy egy mező üres-e
    public boolean ures(int sor, int oszlop) {
        return tabla[sor][oszlop] == 0;
    }
}
