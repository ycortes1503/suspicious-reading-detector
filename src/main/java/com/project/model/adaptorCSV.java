package com.project.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class adaptorCSV extends Adaptor{
    @Override
    public Map<String, List<Reading>> processFile(final File file) {
        Map<String, List<Reading>> mapReadings = new HashMap<>();

        try {
            Scanner sc = new Scanner(file);

            if (sc.hasNextLine()) {
                //skip the header
                sc.nextLine();
            }

            int linesCounter = 0;
            List<Reading> listReadings = new ArrayList<>();

            while (sc.hasNextLine()) {
                String[] line = (sc.nextLine()).split(",");
                Reading reading = new Reading(line[1], Integer.parseInt(line[2]));
                listReadings.add(reading);
                linesCounter++;
                if (linesCounter == 12) {
                    mapReadings.put(line[0], listReadings);
                    listReadings = new ArrayList<>();
                    linesCounter = 0;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return mapReadings;
    }
}
