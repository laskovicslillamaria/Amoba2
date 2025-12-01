package Amoba.Model;

import java.io.Serializable;

public class GameState implements Serializable {
    private final int[][] tabla;
    private final int aktualisJatekos;
    private final String playername;
    private final int lepesSzam;

    public GameState(int[][] tabla, int aktualisJatekos, String playername, int lepesSzam) {
        this.tabla = tabla;
        this.aktualisJatekos = aktualisJatekos;
        this.playername = playername;
        this.lepesSzam = lepesSzam;
    }

    public int[][] getTabla() {
        return tabla;
    }

    public int getAktualisJatekos() {
        return aktualisJatekos;
    }

    public String getPlayername() {
        return playername;
    }

    public int getLepesSzam() {
        return lepesSzam;
    }
    }