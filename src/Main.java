import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            // a)
            String filePath = "spielorte.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray eventsArray = new JSONArray(content);


            System.out.println("Daten:");
            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject event = eventsArray.getJSONObject(i);
                System.out.println(event);
            }


            // c)
            List<String> spielers = new ArrayList<>();
            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject event = eventsArray.getJSONObject(i);
                if (event.getString("Spielort").equals("München")) {
                    String date = event.getString("Datum");
                    String team1 = event.getString("Team1");
                    String team2 = event.getString("Team2");
                    spielers.add(date + ": " + team1 + " vs " + team2);
                }
            }

            spielers.sort(String::compareTo);

            System.out.println("Cei care au jucat in munchen ordonati cronologic:");
            for (String spieler : spielers) {
                System.out.println(spieler);
            }


            //d
            Map<String, Integer> houseCounts = new TreeMap<>();
            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject event = eventsArray.getJSONObject(i);
                String house = event.getString("Spielort");
                houseCounts.put(house, houseCounts.getOrDefault(house, 0) + 1);
            }

            List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(houseCounts.entrySet());
            sortedList.sort((e1, e2) -> {
                int compareCount = e2.getValue().compareTo(e1.getValue());
                if (compareCount == 0) {
                    return e1.getKey().compareTo(e2.getKey());
                }
                return compareCount;
            });

            BufferedWriter writer = new BufferedWriter(new FileWriter("spielanzahl.txt"));
            for (Map.Entry<String, Integer> entry : sortedList) {
                writer.write(entry.getKey() + "%" + entry.getValue());
                writer.newLine();
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
