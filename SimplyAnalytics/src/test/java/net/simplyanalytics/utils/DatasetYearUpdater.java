package net.simplyanalytics.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatasetYearUpdater {

    // Constant defining the new dataset year

    /**
     * Updates the last four-digit year in the input string starting with "20"
     * to the value defined in NEW_YEAR.
     *
     * @param input The string containing the dataset year.
     * @return The updated string with the year replaced, or the original string
     *         if no year is found.
     */
    public static String updateDatasetYear(String input, String year) {
        if (input == null || input.isEmpty()) {
            return input; // Return as is if input is null or empty
        }

        // Regular expression to match four-digit numbers starting with "20"
        Pattern yearPattern = Pattern.compile("20\\d{2}(?!.*20\\d{2})");
        Matcher matcher = yearPattern.matcher(input);

        // Replace the last occurrence of the year with the year variable
        if (matcher.find()) {
            return matcher.replaceFirst(String.valueOf(year));
        }

        // Return the original input if no match is found
        return input;
    }

    /**
     * Updates the years in a list of strings.
     *
     * @param inputList The list of strings to be processed.
     * @return A new list of strings with updated years.
     */
    public static List<String> updateDatasetYearsInList(List<String> inputList, String year) {
        if (inputList == null || inputList.isEmpty()) {
            return inputList; // Return as is if the list is null or empty
        }

        List<String> updatedList = new ArrayList<>();

        for (String input : inputList) {
            // Update each string using the updateDatasetYear method
            String updatedString = updateDatasetYear(input,year);
            updatedList.add(updatedString);
        }

        return updatedList;
    }

    // Test the methods
    public static void main(String[] args) {
        List<String> testStrings = new ArrayList<>();
        testStrings.add("# Households in U1: Urban Elite, 2023");
        testStrings.add("Population Growth since 2019");
        testStrings.add("Census Data Collected in 2020");
        testStrings.add("Historical Data: 1980-2022");

        System.out.println("Original Dataset Strings:");
        for (String s : testStrings) {
            System.out.println(s);
        }

        List<String> updatedStrings = updateDatasetYearsInList(testStrings,"2025");

        System.out.println("\nUpdated Dataset Strings:");
        for (String s : updatedStrings) {
            System.out.println(s);
        }
    }
}
