package net.simplyanalytics.enums;

public enum DataBrowseType {
  
  CATEGORY("Category","category"),
  DATA_FOLDER("Data Folder","dataFolder"),
  MY_IMPORTED_DATA("My Imported Data","myImportedData"),
  ;
  
  private String name;
  private String id;
  
  DataBrowseType(String name, String id) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  @Override
  public String toString() {
    return getName();
  }
  
  public String getId() {
    return id;
  }
  
}
