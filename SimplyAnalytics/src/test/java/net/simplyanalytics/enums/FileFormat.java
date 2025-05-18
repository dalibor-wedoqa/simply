package net.simplyanalytics.enums;

/**
 * .
 *
 * @author wedoqa
 */
public enum FileFormat {
  
  // Only for Map Export
  PNG("PNG", "png"),
  JPEG("JPEG", "jpeg"),
  SVG("SVG", "svg"),
  PDF("PDF", "pdf"),
  
  // For Export of the rest of the Views
  CSV("CSV", "csv"),
  DBF("DBF", "zip"),
  EXCEL("Excel", "xlsx"),
  ;
  
  private final String name;
  private final String extension;
  
  FileFormat(String name, String extension) {
    this.name = name;
    this.extension = extension;
  }
  
  public String getName() {
    return name;
  }
  
  public String getExtension() {
    return extension;
  }
  
  @Override
  public String toString() {
    return name;
  }
  
}
