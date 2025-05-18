package net.simplyanalytics.enums;

public enum HistoricalYearEnum {

  HJ_2000("2000–2009"),
  HJ_1990("1990–1999"),
  HJ_1980("1980–1989"),
  HJ_1970("1970–1979"),
  HJ_1960("1960–1969"),
  HJ_1950("1950–1959");
  
  private String name;

  
  HistoricalYearEnum(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
  public HistoricalYearEnum getHistoricalYearByName(String name) {
    for(HistoricalYearEnum year : HistoricalYearEnum.values()) {
      if (year.getName().equals(name))
        return year;
    }
    throw new AssertionError("Historical year: " + name + " is not supported");
  }
  
}