package net.simplyanalytics.enums;

public enum DMAsLocation {

  ALBANY("Albany, NY", "usa:dma:ab"),
  ALLENTOWN("Allentown, PA", "usa:dma:ud"),
  LOS_ANGELES("Los Angeles, CA", "usa:dma:la"),
  CHICAGO("Chicago, IL", "usa:dma:ch"),
  BOSTON("Boston, MA", "usa:dma:bs"),
  WASHINGTON("Washington, D.C.", "usa:dma:ws")
  ;
  
  private String name;
  private String dataName;
  
  private DMAsLocation(String name, String dataName) {
    this.name = name;
    this.dataName = dataName;
  }

  public String getLocationName() {
    return name;
  }
  
  public String getFullLocationName() {
    return name + " (DMA)";
  }

  public String getDataName() {
    return dataName;
  }
  
  public static DMAsLocation getLocationByName(String locationName) {
    for(DMAsLocation location : DMAsLocation.values()) {
      if(location.getLocationName().equals(locationName)) {
        return location;
      }
    }
    throw new AssertionError("Location with name " + locationName + " not found");
  }
  
}
