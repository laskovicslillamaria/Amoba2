package Amoba.Service;
import Amoba.Board;
import Amoba.Model.GameState;
import lombok.Setter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

@Setter
public class GameService {
    private String playername;
    public GameService(String playername) {
        this.playername = playername;
    }

    // Ellenőrzi, hogy van-e győztes
    public int nyertesVizsgalat(Board board) {
        final int[][] t = board.getTabla();

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

    // Ai lép
    public void aiLep(Board board) {
        // 1) AI megnézi, hogy tud-e nyerni közvetlenül
        for (int sor = 0; sor < 5; sor++) {
            for (int oszlop = 0; oszlop < 5; oszlop++) {
                if (board.getTabla()[sor][oszlop] == 0) {
                    board.getTabla()[sor][oszlop] = 2;
                    if (nyertesVizsgalat(board) == 2) {
                        board.getTabla()[sor][oszlop] = 0;
                        board.lep(sor, oszlop, 2);
                        System.out.println("AI (O) lépett a következő mezőre: [" + sor + "," + oszlop + "]");
                        return;
                    }
                    board.getTabla()[sor][oszlop] = 0;
                }
            }
        }

        // 2) AI blokkolja a játékost
        for (int sor = 0; sor < 5; sor++) {
            for (int oszlop = 0; oszlop < 5; oszlop++) {
                if (board.getTabla()[sor][oszlop] == 0) {
                    board.getTabla()[sor][oszlop] = 1;
                    if (nyertesVizsgalat(board) == 1) {
                        board.getTabla()[sor][oszlop] = 0;
                        board.lep(sor, oszlop, 2);
                        System.out.println("AI (O) lépett a következő mezőre: [" + sor + "," + oszlop + "]");
                        return;
                    }
                    board.getTabla()[sor][oszlop] = 0;
                }
            }
        }
        // 3) különben random lép
        superRandom(board);
    }

    private void superRandom(Board board) {
        final Random rnd = new Random();
        while (true) {
           final int sor = rnd.nextInt(5);
           final int oszlop = rnd.nextInt(5);
            if (board.lep(sor, oszlop, 2)) {
                System.out.println("AI (O) lépett a következő mezőre: [" + sor + "," + oszlop + "]");
                return;
            }
        }
    }

    // Következő játékos meghatározása
    public int kovetkezoJatekos(int aktualis) {
        return (aktualis == 1) ? 2 : 1;
    }

    // Győztes kiírása
    public void nyertesKiiras(int eredmeny, String playername) {
        if (eredmeny == 1)
            System.out.println(playername + " (X) győzött!");
        else if (eredmeny == 2)
            System.out.println("Az Ai (O) győzött!");
        else
            System.out.println("Döntetlen!");
    }
    public boolean jatekMenteseTxt(GameState state, String filename) {
       final List<String> lines = new ArrayList<>();
        // 1. sor: játékos neve
        lines.add(state.getPlayername() == null ? "" : state.getPlayername());
        //2.sor: aktuális játékos
        lines. add(String.valueOf(state.getAktualisJatekos()));
        //3.sor: Lépésszam
        lines.add(String.valueOf(state.getLepesSzam()));

       final int [][] tabla = state.getTabla();
        for (int[] ints : tabla) {
            final StringBuilder sb = new StringBuilder();
            for (int j = 0; j < ints.length; j++) {
                if (j > 0) sb.append(",");
                sb.append(ints[j]);
            }
            lines.add(sb.toString());
        }
        try {
            Files.write(Path.of(filename), lines);
            System.out.println("Játék mentve: " + filename);
            return true;
        } catch (IOException e) {
            System.out.println("Hiba a játék mentésekor: " + e.getMessage());
            return false;
        }
    }
    public GameState jatekBetolteseTxt(String filename) {
       final Path p = Path.of(filename);
        if (!Files.exists(p)) {
            System.out.println("Nincs mentett játék: " + filename);
            return null;
        }
        try {
           final List<String> lines = Files.readAllLines(p);
            if (lines.size() < 8) {
                System.out.println("Érvénytelen mentés (túl rövid): " + filename);
                return null;
            }
           final String playername = lines.get(0);
           final int aktualisJatekos = Integer.parseInt(lines.get(1).trim());
           final int lepesszam = Integer.parseInt(lines.get(2).trim());
           final int [][] tabla = new int [5][5];
            for (int i = 0; i < 5; i++) {
               final String row = lines.get(3 + i).trim();
                final String[] parts = row.split(",");
                if (parts.length != 5) {
                    System.out.println("Érvénytelen táblasor a mentésben: " + row);
                    return null;
                }
                for (int j = 0; j < 5; j++) {
                    tabla[i][j] = Integer.parseInt(parts[j].trim());
                }
            }
            return new GameState(tabla, aktualisJatekos, playername, lepesszam);
        } catch (IOException e) {
            System.out.println("Hiba a fájl olvasásakor: " + e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Hibás számformátum a mentésfájlban: " + e.getMessage());
            return null;
        }
    }

    public void statMentes(int eredmeny, String playername) {
        try (FileWriter fw = new FileWriter("scores.txt", true)) {
            if (eredmeny == 1) {
                fw.write(playername + " nyert\n");
            } else if (eredmeny == 2) {
                fw.write("AI nyert\n");
            } else {
                fw.write("Döntetlen\n");
            }
        } catch (IOException e) {
            System.out.println("Hiba a statisztika mentésekor!");
        }
    }
    public void statKiolvasas() {
        System.out.println("=== STATISZTIKA ===");

        int playerWins = 0;
        int aiWins = 0;
        int draws = 0;

        try {
        final  List<String> sorok = Files.readAllLines(Paths.get("scores.txt"));

            for (String s : sorok) {
                if (s.startsWith("AI")) {
                    aiWins++;
                } else if (s.startsWith("Döntetlen")) {
                    draws++;
                } else {
                    // minden más emberi játékos
                    playerWins++;
                }
            }

            System.out.println(playername + " : " + playerWins + " győzelem");
            System.out.println("AI : " + aiWins + " győzelem");
            System.out.println("Döntetlen : " + draws);

        } catch (IOException e) {
            System.out.println("Még nincs statisztika.");
        }

        System.out.println("====================");
    }

    public String getPlayername() {
        return playername;
    }
}