package net.simplyanalytics.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;

public class CsvUtils {
  
  private static Logger logger = LoggerFactory.getLogger(CompareUtils.class);
  
  public List<String[]> getCsvContent(String decodedString){
    logger.trace("Get Csv content");
    List<String[]> myEntries = null;
    try {
      myEntries = splitCsvString(decodedString);
    } catch (Exception e) {
      logger.error(e.toString());
    }
    return myEntries;
  }
  
  private List<String[]> splitCsvString(String CsvString)  throws Exception {
    try (CSVReader reader = new CSVReader(new StringReader(CsvString))) {
      return reader.readAll();
    }
  }
  
  public List<String[]> getCsvContent(File file) throws Exception{
    logger.info("Get Csv file content");
    String fileString = "";
    String line = "";

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      while ((line = br.readLine()) != null) {
        fileString = fileString + line + "\n";
      }
    }
    List<String[]> csvContent = splitCsvString(fileString);
    return csvContent;
  }
}
