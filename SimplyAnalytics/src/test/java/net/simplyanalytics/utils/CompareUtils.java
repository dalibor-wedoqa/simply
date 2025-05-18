package net.simplyanalytics.utils;

import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.text.StringEscapeUtils;

public class CompareUtils {

  private static Logger logger = LoggerFactory.getLogger(CompareUtils.class);

  public static boolean compareAttachment(List<List<String>> tableContent, List<String[]> fileContent) {
    logger.trace("Compare the content");
    int numberOfRows = tableContent.get(0).size();
    int numberOfColumns = tableContent.size();
    boolean isEqual = true;
    int i = 0;
    int j = 0;
    
    while(isEqual && i < numberOfColumns) {
      j = 0;
      while(isEqual && j < numberOfRows) {
        String cellValueTable = StringEscapeUtils.unescapeHtml4(tableContent.get(i).get(j));
        String cellValueAttachment = fileContent.get(j)[i];
        //Formating values
        String regex = "^\\$?[\\d\\.,]*%?$";
         
        if (Pattern.matches(regex, cellValueTable)) {
          if (cellValueTable.contains("%")) {
            cellValueTable = StringParsing.parsePercentageToString(cellValueTable);
            if (cellValueAttachment.contains("%"))
              cellValueAttachment = StringParsing.parsePercentageToString(cellValueAttachment);
            else
              cellValueAttachment = StringParsing.roundString(cellValueAttachment);
          }
          else if (cellValueTable.contains("$")) {
            cellValueAttachment = StringParsing.roundString(cellValueAttachment.replace("$", "").replace(",", "")).replace(".00", "");
            cellValueTable = StringParsing.parsePriceToString(cellValueTable);
          }
          else {
            cellValueTable = cellValueTable.replace(",", "");
            cellValueAttachment = cellValueAttachment.replace(",", "");
          }
        }
        
        if (cellValueTable.equals("N/A")) {
          cellValueTable = cellValueTable.replace("N/A", "");
        }
         
        String regexNumber = "^\\d+\\.?\\d*$";
        if (cellValueTable.matches(regexNumber)) {
          double cellTable = Double.parseDouble(cellValueTable);
          double cellAttachment = Double.parseDouble(cellValueAttachment);
          if (cellTable != cellAttachment) {
            int min = 0;
            if (cellValueTable.length() <= cellValueAttachment.length()) {
              if (cellValueTable.indexOf(".") != -1)
                min = cellValueTable.length()-cellValueTable.indexOf(".")-1;
            }  
            else if (cellValueAttachment.indexOf(".") != -1) {
              min = cellValueAttachment.length()-cellValueAttachment.indexOf(".")-1;
            }
            double diff = Math.abs(cellAttachment-cellTable);
            diff = StringParsing.roundString(diff, min);
            double diff2 = Math.pow(10,-min);
            if(diff > diff2) {
              logger.error(cellValueAttachment + " != " + cellValueTable);
              isEqual = false;
            } 
          }
        }
        else if(!cellValueAttachment.equals(cellValueTable)) {
          logger.error(cellValueAttachment + " != " + cellValueTable);  
          isEqual = false;
        }
        j++;
      }
      i++;
    }
    return isEqual;  
  }  
}
