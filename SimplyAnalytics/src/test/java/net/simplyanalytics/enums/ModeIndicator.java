package net.simplyanalytics.enums;

public enum ModeIndicator {

  SIMMONS("Simmons NCS Crosstab Data (National-Level): 2023 (Latest)", null),
  SCARBOROUGH("Scarborough Crosstab Data & Locations: 2024 (Latest)", null),
  HISTORICAL_DATA_2010("Historical Data & Locations: 2010 to 2019 Census Geographies", "2010–2019"),
  HISTORICAL_DATA_2000("Historical Data & Locations: 2000 to 2009 Census Geographies", "2000–2009"),
  HISTORICAL_DATA_1990("Historical Data & Locations: 1990 to 1999 Census Geographies", "1990–1999"),
  HISTORICAL_DATA_1980("Historical Data & Locations: 1980 to 1989 Census Geographies", "1980–1989"),
  HISTORICAL_DATA_1970("Historical Data & Locations: 1970 to 1979 Census Geographies", "1970–1979"),
  HISTORICAL_DATA_1960("Historical Data & Locations: 1960 to 1969 Census Geographies", "1960–1969"),
  HISTORICAL_DATA_1950("Historical Data & Locations: 1950 to 1959 Census Geographies", "1950–1959")
  ;
    
  private String name;
  private String year;
    
  ModeIndicator(String name, String year) {
     this.name = name;
     this.year = year;
  }

  public String getName() {
    return name;
  }
  
  public String getYear() {
    return year;
  }

  public static ModeIndicator getModeIndicatorByName(String modeIndicator) {
    for(ModeIndicator mode : ModeIndicator.values()) {
      if (mode.getName().toUpperCase().equals(modeIndicator))
        return mode;
    }
    throw new AssertionError("This mode indicator is not supported: " + modeIndicator);
  }
  
  public static ModeIndicator getModeIndicatorByYear(String year) {
    for(ModeIndicator mode : ModeIndicator.values()) {
      if (mode.getYear() != null && mode.getYear().contains(year))
        return mode;
    }
    throw new AssertionError("This year is not supported: " + year);
  }
  
}
