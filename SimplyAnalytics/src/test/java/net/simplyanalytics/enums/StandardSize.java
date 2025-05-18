package net.simplyanalytics.enums;

/**
 *
 * @author wedoqa
 */
public enum StandardSize {
  
  LETTER("Letter"),
  LEGAL("Legal"),
  TABLOID("Tabloid"),
  ;
  
  private final String value;
  
  StandardSize(String value) {
    this.value = value;
  }
  
  public String getValue() {
    return value;
  }
  
  @Override
  public String toString() {
    return value;
  }
}
