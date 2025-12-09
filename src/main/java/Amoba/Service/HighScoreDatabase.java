package Amoba.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class HighScoreDatabase {
    private static final String FILE_NAME = "highscores.json";
    private final ObjectMapper mapper = new ObjectMapper();
    private ObjectNode root;

    public HighScoreDatabase() {
        load();
    }

    private void load() {
        try {
           final File file = new File(FILE_NAME);
            if (!file.exists()) {
                root = mapper.createObjectNode();
                root.putObject("players");
                save();
            } else {
                root = (ObjectNode) mapper.readTree(file);
            }
        } catch (IOException e) {
            throw new RuntimeException("Hiba az adatbázis betöltésekor", e);
        }
    }

    private void save() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_NAME), root);
        } catch (IOException e) {
            throw new RuntimeException("Hiba az adatbázis mentésénél", e);
        }
    }

    /** Győzelem hozzáadása a játékoshoz */
    public void addWin(String playerName) {
       final ObjectNode players = (ObjectNode) root.get("players");

       final int jelenlegi = players.has(playerName) ? players.get(playerName).asInt() : 0;
        players.put(playerName, jelenlegi + 1);

        save();
    }

    /** High-score lista kiírása */
    public void printHighScores() {
       final ObjectNode players = (ObjectNode) root.get("players");

        System.out.println("===== HIGH SCORES =====");
        players.fields().forEachRemaining(entry ->
                System.out.println(entry.getKey() + " : " + entry.getValue().asInt() + " győzelem")
        );
        System.out.println("========================");
    }
}
