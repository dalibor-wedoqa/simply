package net.simplyanalytics.enums;

public enum DataFormat {
  
  INTEGER("Integer"),
  CURRENCY("Currency"),
  TWO_DECIMA_PLACES("2 Decimal Places");
    
  private final String type;
  
  DataFormat(String type) {
    this.type = type;
  }
  
  public String getType() {
    return type;
  }
  
}
