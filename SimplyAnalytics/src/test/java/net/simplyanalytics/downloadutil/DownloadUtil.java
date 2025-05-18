package net.simplyanalytics.downloadutil;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import net.simplyanalytics.enums.ViewType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */

public class DownloadUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(DownloadUtil.class);

  private static File[] fileDownloading(String dirName, final String documentName) {
    File dir = new File(dirName);
    File[] files = dir.listFiles((File dir1, String filename) -> {
      return filename.contains(documentName) && filename.endsWith(".crdownload");
    });
    if (files == null) {
      files = new File[0];
    }
    return files;
  }

  private static File[] finder(String dirName, final String documentName, String fileExtension) {
    File dir = new File(dirName);
    LOGGER.trace("dirName: " + dirName);
    return dir.listFiles((dir1, filename) -> {
      LOGGER.trace("found file: " + filename);
      return filename.contains(documentName) && filename.endsWith("." + fileExtension);
    });
  }

  // TODO check this method
  /**
   * Checking if file is downloaded.
   * 
   * @param viewType     view type
   * @param fileFormat   file format
   * @param downloadPath download path
   * @return
   */
  public static boolean isFileDownloaded(ViewType viewType, String fileFormat,
      String downloadPath) {

    LOGGER.trace("Verifing that the file is downloaded");
    // String time = DateTimeFormatter.ofPattern("HH-mm").format(LocalTime.now());

    boolean isFileDownloaded = false;

    LOGGER.trace("downloadPath: " + downloadPath);
    LOGGER.trace("Expected time: " + DateTimeFormatter.ofPattern("HH-mm").format(LocalTime.now()));

    String documentName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH-mm").format(LocalTime.now());

    LOGGER.trace("documentName: " + documentName);

    String documentName1 = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH-mm").format(LocalTime.now().plusMinutes(1));

    LOGGER.trace("documentName1: " + documentName1);

    String documentName2 = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH-mm").format(LocalTime.now().minusMinutes(1));

    LOGGER.trace("documentName2: " + documentName2);

    int counter = 0;
    while (counter < 60) {
      if (!new File(downloadPath).exists()) {
        LOGGER.trace("no download folder");
        LOGGER.trace(downloadPath);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new RuntimeException(e);
        }
      } else {
        if (fileDownloading(downloadPath, documentName).length > 0
            || fileDownloading(downloadPath, documentName1).length > 0
            || fileDownloading(downloadPath, documentName2).length > 0) {
          try {
            LOGGER.trace("sleep 1000");
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);

          }
        } else {
          File[] files = finder(downloadPath, documentName, fileFormat.toLowerCase());
          File[] files1 = finder(downloadPath, documentName1, fileFormat.toLowerCase());
          File[] files2 = finder(downloadPath, documentName2, fileFormat.toLowerCase());

          if (files.length == 1) {
            isFileDownloaded = true;
            files[0].delete();
            counter = 60;
          } else if (files1.length == 1) {
            isFileDownloaded = true;
            files1[0].delete();
            counter = 60;
          } else if (files2.length == 1) {
            isFileDownloaded = true;
            files2[0].delete();
            counter = 60;
          } else {
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          }
        }
      }
      counter++;
    }
    LOGGER.trace("The file is downloaded: " + isFileDownloaded);
    return isFileDownloaded;
  }

  

  public static File getDownloadedFile(ViewType viewType, String fileFormat,
      String downloadPath) {

    LOGGER.trace("Verifing that the file is downloaded");
    // String time = DateTimeFormatter.ofPattern("HH-mm").format(LocalTime.now());

    boolean isFileDownloaded = false;

    LOGGER.trace("downloadPath: " + downloadPath);
    LOGGER.trace("Expected time: " + DateTimeFormatter.ofPattern("HH-mm").format(LocalTime.now()));

    String documentName = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH-mm").format(LocalTime.now());

    LOGGER.trace("documentName: " + documentName);

    String documentName1 = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH-mm").format(LocalTime.now().plusMinutes(1));

    LOGGER.trace("documentName1: " + documentName1);

    String documentName2 = "New Project_" + viewType.getDefaultName() + "_" + LocalDate.now() + "_"
        + DateTimeFormatter.ofPattern("HH-mm").format(LocalTime.now().minusMinutes(1));

    LOGGER.trace("documentName2: " + documentName2);
    File file = null;
    int counter = 0;
    while (counter < 60) {
      if (!new File(downloadPath).exists()) {
        LOGGER.trace("no download folder");
        LOGGER.trace(downloadPath);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new RuntimeException(e);
        }
      } else {
        if (fileDownloading(downloadPath, documentName).length > 0
            || fileDownloading(downloadPath, documentName1).length > 0
            || fileDownloading(downloadPath, documentName2).length > 0) {
          try {
            LOGGER.trace("sleep 1000");
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);

          }
        } else {
          File[] files = finder(downloadPath, documentName, fileFormat.toLowerCase());
          File[] files1 = finder(downloadPath, documentName1, fileFormat.toLowerCase());
          File[] files2 = finder(downloadPath, documentName2, fileFormat.toLowerCase());

          if (files.length == 1) {
            isFileDownloaded = true;
            file = files[0];
            counter = 60;
          } else if (files1.length == 1) {
            isFileDownloaded = true;
            file = files1[0];
            counter = 60;
          } else if (files2.length == 1) {
            isFileDownloaded = true;
            file = files2[0];
            counter = 60;
          } else {
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          }
        }
      }
      counter++;
    }
    LOGGER.trace("The file is downloaded: " + isFileDownloaded);
    return file;
  }
  
  public static boolean deleteDownloadedFile(File file) {
    LOGGER.info("Delete downloaded file");
    return file.delete();
  }
  
  
  
}
