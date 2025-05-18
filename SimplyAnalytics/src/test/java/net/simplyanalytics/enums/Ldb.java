package net.simplyanalytics.enums;

public enum Ldb {
  
  LOCATION("Location", "Locations"),
  DATA("Data", "Data"),
  BUSINESS("Business", "Businesses"),;
  
  private String singularName;
  private String pluralName;
  
  Ldb(String singularName, String pluralName) {
    this.singularName = singularName;
    this.pluralName = pluralName;
  }
  
  @SuppressWarnings("ucd")
  public String getSingularName() {
    return singularName;
  }
  
  public String getPluralName() {
    return pluralName;
  }
  
}
