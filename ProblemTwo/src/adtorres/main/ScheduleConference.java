package adtorres.main;

import adtorres.utils.ReadFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 * @author Alfredo David Torres Villalta
 *
 */

public class ScheduleConference {
	
    /**
     * Get a schedule instance
     */
    public static void getSchedule() {
        
        int timeMorning = 180;
        int timeAfternoon = 240;
    	
    	Map<String, Integer> titleAndTime = ReadFile.readTalksList2Map();
        Map<String, Integer> titleAndTimeBak = new HashMap<String, Integer>(titleAndTime);
        Map<String, Integer> track1Morning = getTrackMap(titleAndTime, timeMorning);
        Map<String, Integer> track1Afternoon = getTrackMap(titleAndTime, timeAfternoon);
        Map<String, Integer> track2Morning = getTrackMap(titleAndTime, timeMorning);
        Map<String, Integer> track2Afternoon = getTrackMap(titleAndTime, timeAfternoon);
        
        if (!titleAndTime.isEmpty()) {
            Map<String, Integer> tempMap = new HashMap<String, Integer>(titleAndTimeBak);
            while(!tempMap.isEmpty()){
                tempMap = new HashMap<String, Integer>(titleAndTimeBak);
                track1Morning = getTrackMap(tempMap, timeMorning);
                track1Afternoon = getTrackMap(tempMap, timeAfternoon);
                track2Morning = getTrackMap(tempMap, timeMorning);
                track2Afternoon = getTrackMap(tempMap, timeAfternoon);
            }
        }
        System.out.println("Test output:");
        
        System.out.println("Track 1:");
        morningSchedule(track1Morning);
        afternoonSchedule(track1Afternoon);
        System.out.println("Track 2:");
        morningSchedule(track2Morning);
        afternoonSchedule(track2Afternoon);

    }

    /**
     * Morning schedule
     * 
     * @param trackMap
     */
    public static void morningSchedule(Map<String, Integer> trackMap) {
        int sumTime = 0;
        int res = 0;
        String remainderStr = "00";
        for (Entry<String, Integer> entry : trackMap.entrySet()) {
            String title = entry.getKey();
            int time = entry.getValue();
            String timeStr = time == 5 ? "lightning" : time + "";
            switch (res) {
            case 0:
                System.out.println("09:" + remainderStr + "AM " + title + " " + timeStr + "min");
                break;
            case 1:
                System.out.println("10:" + remainderStr + "AM " + title + " " + timeStr + "min");
                break;
            case 2:
                System.out.println("11:" + remainderStr + "AM " + title + " " + timeStr + "min");
                break;
            default:
                break;
            }
            sumTime += time;
            res = sumTime / 60;
            int remainder = sumTime % 60;
            if (remainder / 10 == 0) {
                remainderStr = "0" + remainder;
            } else {
                remainderStr = remainder + "";
            }
        }
        System.out.println("12:00PM Lunch");
    }

    /**
     * Afternoon schedule
     * 
     * @param trackMap
     */
    public static void afternoonSchedule(Map<String, Integer> trackMap) {
        int sumTime = 0;
        int res = 0;
        String remainderStr = "00";
        for (Entry<String, Integer> entry : trackMap.entrySet()) {
            String title = entry.getKey();
            int time = entry.getValue();
            String timeStr = time == 5 ? "lightning" : time + "";
            switch (res) {
            case 0:
                System.out.println("01:" + remainderStr + "PM " + title + " " + timeStr + "min");
                break;
            case 1:
                System.out.println("02:" + remainderStr + "PM " + title + " " + timeStr + "min");
                break;
            case 2:
                System.out.println("03:" + remainderStr + "PM " + title + " " + timeStr + "min");
                break;
            case 3:
                System.out.println("04:" + remainderStr + "PM " + title + " " + timeStr + "min");
                break;
            default:
                break;
            }
            sumTime += time;
            res = sumTime / 60;
            int remainder = sumTime % 60;
            if (remainder / 10 == 0) {
                remainderStr = "0" + remainder;
            } else {
                remainderStr = remainder + "";
            }
        }
        System.out.println("05:00PM Networking Event");
    }

    /**
     * Get each session track
     * 
     * @param titleAndTime
     * @param totalMinute
     * @return
     */
    public static Map<String, Integer> getTrackMap(Map<String, Integer> titleAndTime, int sessionMinute) {
        Map<String, Integer> trackMap = new HashMap<String, Integer>();
        List<String> titleList = new ArrayList<String>(titleAndTime.keySet());
        Random random = new Random();
        int randomIndex = 0;
        String randomTitle = null;
        int time = 0;
        int sumTime = 0;
        
        int flag = 0;
        
        //Add title and time to schedule 
        while (sumTime < sessionMinute && titleList.size() > 0) {
            randomIndex = random.nextInt(titleList.size());
            randomTitle = titleList.get(randomIndex);
            time = titleAndTime.get(randomTitle);
            sumTime += time;
            
            //Validate if there is still time for a new session 
            if (sumTime <= sessionMinute) {
            	flag = sessionMinute - sumTime;
                trackMap.put(randomTitle, time);
            }
            titleList.remove(randomTitle);
        }

        // Remove entry from title and time which has already schedule
        Set<String> trackMapKeySet = trackMap.keySet();
        Iterator<Entry<String, Integer>> it = titleAndTime.entrySet().iterator();
        while (it.hasNext()) {
            if (trackMapKeySet.contains(it.next().getKey())) {
                it.remove();
            }
        }
        return trackMap;
    }
}
