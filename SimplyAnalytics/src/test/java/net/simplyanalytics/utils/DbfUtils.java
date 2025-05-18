package net.simplyanalytics.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linuxense.javadbf.DBFReader;

public class DbfUtils {
  
  private static Logger logger = LoggerFactory.getLogger(CompareUtils.class);

  public List<String[]> getDbfContent(InputStream is) throws IOException {
    /* Get DBF file from ZIP file */
    
    logger.trace("Get Dbf content");
    try (ZipInputStream zip = new ZipInputStream(is)) {
      ZipEntry entry = null;
  
      InputStream dbfInputStream = null;
      
      while ((entry = zip.getNextEntry()) != null) {
          String entryName = entry.getName();
          if (entryName.endsWith(".dbf")) {
            dbfInputStream = convertToInputStream(zip);
            break;
          }
      }
  
      List<String[]> myEntries = new ArrayList<>();
      try (DBFReader reader = new DBFReader(dbfInputStream)) {
        
        int numberOfFields = reader.getFieldCount();
        
        /*Get fields values from DBF file*/  
        myEntries.add(null);  //Method compareAttachment starts with 1. row (not 0. row)
        Object[] rowObjects;
        
        while ((rowObjects = reader.nextRecord()) != null) {
          String[] row = new String[numberOfFields];
          for (int i = 0; i < rowObjects.length; i++) {
            if(rowObjects[i] == null) {
              row[i] = "";
            } else {
              row[i] = rowObjects[i].toString();
            }
          }
          myEntries.add(row);
        }
      }
      dbfInputStream.close();
      return myEntries;
    }
  }
  
  private static InputStream convertToInputStream(final ZipInputStream inputStreamIn) throws IOException {
    try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
    IOUtils.copy(inputStreamIn, out);
    return new ByteArrayInputStream(out.toByteArray());
    }
  }
}
