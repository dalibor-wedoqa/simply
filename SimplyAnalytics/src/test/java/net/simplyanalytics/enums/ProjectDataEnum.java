package net.simplyanalytics.enums;

public enum ProjectDataEnum {

  EXCLUDING_PROJECT_DATA("Excluding Projected Data"),
  INCLUDING_PROJECT_DATA("Including Projected Data"),
  ;
  
  private String name;
  
  ProjectDataEnum(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
}
