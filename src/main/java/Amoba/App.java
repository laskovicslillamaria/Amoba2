package Amoba;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Létrehozzuk a 3x3-as játéktáblát
        int[][] tabla = tablaInicializalas();

        // Az első játékos kezd (1 = játékos 1, 2 = játékos 2)
        int aktualisJatekos = 1;

        // Eredmény: 0 = még nincs vége, 1 = játékos 1 nyert, 2 = játékos 2 nyert, 3 = döntetlen
        int eredmeny = 0;

        // Lépések számlálója (max 9 lépés lehetséges egy 3x3-as táblán)
        int lepesSzam = 0;

        Scanner sc = new Scanner(System.in);
        // A játék addig tart, amíg nincs nyertes vagy döntetlen
        do {
            tablaKirajzolas(tabla);                          // Megjelenítjük a táblát
            ervenyesLepes(sc, tabla, aktualisJatekos);        // Bekérjük a játékos lépését
            eredmeny = nyertesVizsgalat(tabla);               // Ellenőrizzük, van-e nyertes
            if (eredmeny == 0) {
                aktualisJatekos = kovetkezoJatekos(aktualisJatekos);  // Következő játékos jön
                lepesSzam++;
                if (lepesSzam == 25) {
                    eredmeny = 5; // Döntetlen
                }
            }
        } while (eredmeny == 0);

        tablaKirajzolas(tabla);  // Végső tábla megjelenítése
        nyertesKiiras(eredmeny); // Győztes vagy döntetlen kiírása
    }
    // Létrehozunk egy üres 5x5-as táblát (minden mező 0)
    public static int[][] tablaInicializalas() {
        return new int[5][5];
    }
    // Kiírjuk a tábla aktuális állapotát a konzolra
    public static void tablaKirajzolas(int[][] tabla) {
        System.out.println("+-+-+-+-+-+");
        for (int i = 0; i < 5; i++) {
            System.out.print("|");
            for (int j = 0; j < 5; j++) {
                int ertek = tabla[i][j];
                // Szebb, emberibb megjelenítés (0 = üres mező)
                if (ertek == 0) {
                    System.out.print(" |");
                } else if (ertek == 1) {
                    System.out.print("X|");  // Játékos 1 jele
                } else {
                    System.out.print("O|");  // Játékos 2 jele
                }
            }
            System.out.println();
            System.out.println("+-+-+-+-+-+");
        }
    }
    // Játékos lépést ad meg, ami csak érvényes pozíció lehet
    public static void ervenyesLepes(Scanner sc, int[][] tabla, int aktualisJatekos) {
        System.out.println("Játékos " + aktualisJatekos + " következik.");

        int sor, oszlop;

        while (true) {
            System.out.print("Add meg a sort (0-4): ");
            sor = sc.nextInt();
            System.out.print("Add meg az oszlopot (0-4): ");
            oszlop = sc.nextInt();

            // Érvényesség ellenőrzés: 0-4 tartományban van és a mező még üres?
            if (sor >= 0 && sor < 5 && oszlop >= 0 && oszlop < 5 && tabla[sor][oszlop] == 0) {
                break;  // Lépés elfogadva
            }

            System.out.println("Hibás mező! Próbáld újra.");
        }

        // Lépés rögzítése a táblán
        tabla[sor][oszlop] = aktualisJatekos;
    }
    // Megvizsgáljuk, hogy van-e győztes
    public static int nyertesVizsgalat(int[][] tabla) {
        // Sorok és oszlopok ellenőrzése
        for (int i = 0; i < 5; i++) {
            // Sorok
            if (tabla[i][0] != 0 && tabla[i][0] == tabla[i][1] && tabla[i][1] == tabla[i][2] && tabla[i][2] == tabla[i][3] && tabla [i][3] == tabla[i][4]) {
                return tabla[i][0]; // Visszaadjuk a nyertes játékos számát
            }

            // Oszlopok
            if (tabla[0][i] != 0 && tabla[0][i] == tabla[1][i] && tabla[1][i] == tabla[2][i] && tabla[2][i] == tabla[3][i] && tabla[3][i] == tabla[4][i]) {
                return tabla[0][i];
            }
        }

        // Átlók ellenőrzése
        if (tabla[0][0] != 0 && tabla[0][0] == tabla[1][1] && tabla[1][1] == tabla[2][2] && tabla[2][2] == tabla[3][3] && tabla[3][3] == tabla[4][4]) {
            return tabla[0][0];
        }

        if (tabla[0][4] != 0 && tabla[0][4] == tabla[1][3] && tabla[1][3] == tabla[2][2] && tabla[2][2] == tabla[3][1] && tabla[3][1] == tabla[4][0]) {
            return tabla[0][4];
        }

        // Nincs nyertes
        return 0;
    }

    // Váltás a következő játékosra
    public static int kovetkezoJatekos(int aktualisJatekos) {
        return (aktualisJatekos == 1) ? 2 : 1;
    }

    // Kiírjuk az eredményt
    public static void nyertesKiiras(int eredmeny) {
        if (eredmeny == 1) {
            System.out.println("Az 1-es (X) játékos győzött!");
        } else if (eredmeny == 2) {
            System.out.println("A 2-es (O) játékos győzött!");
        } else if (eredmeny == 3) {
            System.out.println("Döntetlen!");
        }
    }
}
