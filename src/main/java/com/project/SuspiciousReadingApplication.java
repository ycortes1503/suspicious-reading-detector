package com.project;

import com.project.utils.ReadingUtil;

import java.util.*;

public class SuspiciousReadingApplication {
    private static final String END_COMMAND = "end";

    public static void main(String[] args) {
        String str = "";
        do {
            System.out.print("Please enter the name of the file to be processed or 'end' to finish the execution: ");
            Scanner scInput = new Scanner(System.in);
            str = scInput.nextLine();
            ReadingUtil.processInput(str);
        } while (!str.equalsIgnoreCase(END_COMMAND));
    }
}
