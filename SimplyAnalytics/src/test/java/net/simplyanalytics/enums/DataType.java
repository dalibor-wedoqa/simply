package net.simplyanalytics.enums;

public enum DataType {
  
  ALL("All"),
  LOCATION_ID("Location ID"),
  COUNT("Count"),
  MEAN("Mean"),
  MEDIAN("Median"),
  PERCENT("Percent"),
  AVERAGE("Average");
  
  private final String type;
  
  DataType(String type) {
    this.type = type;
  }
  
  public String getType() {
    return type;
  }
}
