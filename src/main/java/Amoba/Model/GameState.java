package Amoba.Model;

public class GameState {
    private int[][] tabla;
    private int aktualisJatekos;
    private String playername;
    private int lepesSzam;

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