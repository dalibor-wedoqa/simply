package net.simplyanalytics.enums;

public enum SearchMenuType {
  
  RECENT("Recent"),
  FAVORITE("Favorite");
  
  private final String name;
  
  SearchMenuType(String name) {
    this.name = name;
  }
  
  @SuppressWarnings("ucd")
  public String getName() {
    return name;
  }
}
