package com.project.utils;

import com.project.SuspiciousReadingApplication;
import com.project.model.Adaptor;
import com.project.model.Reading;
import com.project.model.adaptorCSV;
import com.project.model.adaptorXML;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ReadingUtil {
    private static final double PERCENTAGE = 50;
    public static Map<String, List<Reading>> processInput (final String str) {
        Map<String, List<Reading>> mapReadings = new HashMap<>();
        Map<String, List<Reading>> mapSuspiciousReading = new HashMap<>();
        File file = null;

        if (SuspiciousReadingApplication.class.getClassLoader().getResource(str) != null) {
            file = new File(Objects.requireNonNull(SuspiciousReadingApplication.class.getClassLoader().getResource(str)).getFile());
        }

        String command =  file != null ? FilenameUtils.getExtension(file.getName()) : str.toLowerCase();
        Adaptor adaptor = null;

        switch(command) {
            case "csv":
                adaptor = new adaptorCSV();
                break;
            case "xml":
                adaptor = new adaptorXML();
                break;
            case "end":
                System.out.println("Execution ended by the user.");
                break;
            default:
                System.out.println("Input invalid or not supported yet.");
                break;
        }

        if (adaptor != null) {
            mapReadings = adaptor.processFile(file);

            for (String client : mapReadings.keySet()) {
                List<Reading> suspiciousReadings = ReadingUtil.getSuspiciousReadings(mapReadings.get(client));
                if (!suspiciousReadings.isEmpty()) {
                    mapSuspiciousReading.put(client, suspiciousReadings);
                }
            }

            printSuspiciousReadings(mapReadings, mapSuspiciousReading);
        }
        return mapSuspiciousReading;
    }

    public static List<Reading> getSuspiciousReadings(final List<Reading> annualReadings) {
        List<Reading> suspiciousReadings = new ArrayList<>();
        OptionalDouble averageOptional = annualReadings.stream().mapToInt(Reading::getValue).average();
        if (averageOptional.isPresent()) {
            double average = averageOptional.getAsDouble();
            double variation = average * (PERCENTAGE/100);
            double minValidValue = average - variation;
            double maxValidValue = average + variation;

            suspiciousReadings = annualReadings.stream()
                    .filter(reading -> reading.getValue() < (int) minValidValue || reading.getValue() > (int) maxValidValue)
                    .collect(Collectors.toList());
        }

        return suspiciousReadings;
    }

    public static void printSuspiciousReadings (final Map<String, List<Reading>> mapReadings, final Map<String, List<Reading>> mapSuspiciousReadings) {
        System.out.format("| Client          | Month      | Suspicious      | Median          |%n");
        System.out.format("--------------------------------------------------------------------%n");

        for (String client : mapSuspiciousReadings.keySet()) {
            String leftAlignFormat = "| %-15s | %-10s | %-15d | %-15d |%n";

            List<Reading> suspiciousReadings = mapSuspiciousReadings.get(client);
            List<Reading> readings = mapReadings.get(client);
            OptionalDouble averageOptional = readings.stream().mapToInt(Reading::getValue).average();

            suspiciousReadings.forEach(sr -> {
                String month = sr.getPeriod().split("-")[1];
                System.out.format(leftAlignFormat, client, month, sr.getValue(), (int) averageOptional.getAsDouble());
            });
        }

    }
}
