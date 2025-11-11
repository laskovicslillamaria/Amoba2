package Amoba;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testTablaInicializalas() {
        int[][] tabla = App.tablaInicializalas();
        assertEquals(5, tabla.length);
        assertEquals(5, tabla[0].length);

        // Minden mező 0 legyen
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(0, tabla[i][j]);
            }
        }
    }

    @Test
    public void testKovetkezoJatekos() {
        assertEquals(2, App.kovetkezoJatekos(1));
        assertEquals(1, App.kovetkezoJatekos(2));
    }

    @Test
    public void testNyertesVizsgalatSor() {
        int[][] tabla = new int[5][5];
        // Első sor tele X-el
        for (int j = 0; j < 5; j++) {
            tabla[0][j] = 1;
        }
        assertEquals(1, App.nyertesVizsgalat(tabla));
    }

    @Test
    public void testNyertesVizsgalatOszlop() {
        int[][] tabla = new int[5][5];
        // Második oszlop tele O-val
        for (int i = 0; i < 5; i++) {
            tabla[i][1] = 2;
        }
        assertEquals(2, App.nyertesVizsgalat(tabla));
    }

    @Test
    public void testNyertesVizsgalatAtloBalrolJobbra() {
        int[][] tabla = new int[5][5];
        for (int i = 0; i < 5; i++) {
            tabla[i][i] = 1;
        }
        assertEquals(1, App.nyertesVizsgalat(tabla));
    }

    @Test
    public void testNyertesVizsgalatAtloJobbrolBalra() {
        int[][] tabla = new int[5][5];
        for (int i = 0; i < 5; i++) {
            tabla[i][4 - i] = 2;
        }
        assertEquals(2, App.nyertesVizsgalat(tabla));
    }

    @Test
    public void testNincsNyertes() {
        int[][] tabla = new int[5][5];
        tabla[0][0] = 1;
        tabla[1][1] = 2;
        tabla[2][2] = 1;
        assertEquals(0, App.nyertesVizsgalat(tabla));
    }
}
