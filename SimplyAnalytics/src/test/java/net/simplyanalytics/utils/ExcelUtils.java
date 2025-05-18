package net.simplyanalytics.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {

  private static Logger logger = LoggerFactory.getLogger(CompareUtils.class);
  
  public List<String[]> getExcelContent(InputStream is) throws Exception {
    /* read excel values */
    logger.trace("Get Excel content");
    List<List<String>> excelValues = new ArrayList<>();
    try (XSSFWorkbook wb = new XSSFWorkbook(is)) {
      XSSFSheet sheet=wb.getSheetAt(0);
      FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();  
      DataFormatter df = new DataFormatter();
      for(Row row : sheet) {
        List<String> rowList = new ArrayList<>();
        for(Cell cell : row) {
          switch(formulaEvaluator.evaluateInCell(cell).getCellType()) {
            case  NUMERIC: rowList
              .add(String.valueOf(df.formatCellValue(cell)));
              break;
            case  STRING : rowList.add(cell.getStringCellValue());
              break;
            default :  rowList.add("");
          }
        }
        excelValues.add(rowList); 
      }
    }
   
    /* Convert List<List<String>> to List<String[]> */
    List<String[]> myEntries = new ArrayList<>();
    for(List<String> str : excelValues) {
      String[] array = new String[str.size()];
      for(int i = 0; i < str.size(); i++) {
        array[i] = str.get(i);
      }
      myEntries.add(array);
    }
    return myEntries;
  }
  
}
