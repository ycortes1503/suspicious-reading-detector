import com.project.model.Reading;
import com.project.utils.ReadingUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadingTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testTableHeader() {
        String expectedHeader = "| Client          | Month      | Suspicious      | Median          |";
        Map<String, List<Reading>> mapSuspiciousReadings = ReadingUtil.processInput("2016-readings.xml");
        Assertions.assertEquals(mapSuspiciousReadings.size(), 4);
        Assertions.assertTrue(outContent.toString().contains(expectedHeader));
    }

    @Test
    public void testInvalidInput() {
        String expectedOutput = "Input invalid or not supported yet.\n";
        Map<String, List<Reading>> mapSuspiciousReadings = ReadingUtil.processInput("readings.txt");
        Assertions.assertEquals(mapSuspiciousReadings.size(), 0);
        Assertions.assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testEndCommand() {
        String expectedOutput = "Execution ended by the user.\n";
        Map<String, List<Reading>> mapSuspiciousReadings = ReadingUtil.processInput("END");
        Assertions.assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void multipleSuspiciousReadingsCSV() {
        String clientID_1 = "583ef6329d7b9";
        String clientID_2 = "583ef6329d89b";
        String clientID_3 = "583ef6329d916";

        Map<String, List<Reading>> expectedMap = new HashMap<>();
        List<Reading> readingsExpectedC1 = new ArrayList<>();
        readingsExpectedC1.add(new Reading("2016-09",3564));

        List<Reading> readingsExpectedC2 = new ArrayList<>();
        readingsExpectedC2.add(new Reading("2016-09",162078));
        readingsExpectedC2.add(new Reading("2016-10",7759));

        List<Reading> readingsExpectedC3 = new ArrayList<>();
        readingsExpectedC3.add(new Reading("2016-09",2479));

        expectedMap.put(clientID_1, readingsExpectedC1);
        expectedMap.put(clientID_2, readingsExpectedC2);
        expectedMap.put(clientID_3, readingsExpectedC3);

        Map<String, List<Reading>> mapSuspiciousReadings = ReadingUtil.processInput("2016-readings.csv");

        Assertions.assertEquals(mapSuspiciousReadings.size(), 3);
        Assertions.assertEquals(expectedMap, mapSuspiciousReadings);
    }

    @Test
    public void multipleSuspiciousReadingsXML() {
        String clientID_1 = "583ef6329e271";
        String clientID_2 = "583ef6329e237";
        String clientID_3 = "583ef6329e41b";
        String clientID_4 = "583ef6329e3ab";

        Map<String, List<Reading>> expectedMap = new HashMap<>();
        List<Reading> readingsExpectedC1 = new ArrayList<>();
        readingsExpectedC1.add(new Reading("2016-10",121208));

        List<Reading> readingsExpectedC2 = new ArrayList<>();
        readingsExpectedC2.add(new Reading("2016-11",1379));

        List<Reading> readingsExpectedC3 = new ArrayList<>();
        readingsExpectedC3.add(new Reading("2016-05",133369));

        List<Reading> readingsExpectedC4 = new ArrayList<>();
        readingsExpectedC4.add(new Reading("2016-11",6440));

        expectedMap.put(clientID_1, readingsExpectedC1);
        expectedMap.put(clientID_2, readingsExpectedC2);
        expectedMap.put(clientID_3, readingsExpectedC3);
        expectedMap.put(clientID_4, readingsExpectedC4);

        Map<String, List<Reading>> mapSuspiciousReadings = ReadingUtil.processInput("2016-readings.xml");

        Assertions.assertEquals(mapSuspiciousReadings.size(), 4);
        Assertions.assertEquals(expectedMap, mapSuspiciousReadings);
    }

    @Test
    public void oneLowerOneHigherSuspiciousReadingCSV() {
        String clientID_1 = "583ef6329d89b";

        Map<String, List<Reading>> expectedMap = new HashMap<>();

        List<Reading> readingsExpectedC1 = new ArrayList<>();
        readingsExpectedC1.add(new Reading("2016-09",162078));
        readingsExpectedC1.add(new Reading("2016-10",7759));

        expectedMap.put(clientID_1, readingsExpectedC1);

        Map<String, List<Reading>> mapSuspiciousReadings = ReadingUtil.processInput("583ef6329d89b.csv");

        Assertions.assertEquals(mapSuspiciousReadings.size(), 1);
        Assertions.assertEquals(expectedMap, mapSuspiciousReadings);
    }

    @Test
    public void oneLowerSuspiciousReading() {
        String clientID_1 = "583ef6329e237";

        Map<String, List<Reading>> expectedMap = new HashMap<>();

        List<Reading> readingsExpectedC1 = new ArrayList<>();
        readingsExpectedC1.add(new Reading("2016-11",1379));

        expectedMap.put(clientID_1, readingsExpectedC1);

        Map<String, List<Reading>> mapSuspiciousReadings = ReadingUtil.processInput("583ef6329e237.xml");

        Assertions.assertEquals(mapSuspiciousReadings.size(), 1);
        Assertions.assertEquals(expectedMap, mapSuspiciousReadings);
    }

    @Test
    public void oneHigherSuspiciousReading() {
        String clientID_1 = "583ef6329e41b";

        Map<String, List<Reading>> expectedMap = new HashMap<>();

        List<Reading> readingsExpectedC1 = new ArrayList<>();
        readingsExpectedC1.add(new Reading("2016-05",133369));

        expectedMap.put(clientID_1, readingsExpectedC1);

        Map<String, List<Reading>> mapSuspiciousReadings = ReadingUtil.processInput("583ef6329e41b.xml");

        Assertions.assertEquals(mapSuspiciousReadings.size(), 1);
        Assertions.assertEquals(expectedMap, mapSuspiciousReadings);
    }

}
