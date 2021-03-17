package adtorres.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

/**
 * @author Alfredo David Torres Villalta
 *
 */

public class ReadFile {
    /**
     * Read conferences.txt and return map with title and time
     * 
     * @return map, key:title; value:time;
     */
    public static Map<String, Integer> readTalksList2Map() {
        Map<String, Integer> titleAndTime = new HashMap<String, Integer>();
        File file = new File("assets/conferences.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            int numTime = 0;
            while ((line = reader.readLine()) != null) {
                int lastIndexBlank = line.lastIndexOf(" ");
                String title = line.substring(0, lastIndexBlank);
                String time = line.substring(lastIndexBlank + 1);
                
                if (time.equals("lightning")) {
                    numTime = 5;
                } else {
                    numTime = Integer.parseInt(time.substring(0, time.length() - 3));
                }
                titleAndTime.put(title, numTime);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                	System.out.println("Error: "+e1.getMessage());
                }
            }
        }
        return titleAndTime;
    }
}
