package net.simplyanalytics.enums;

public enum SearchSubMenuType {
  
  RECENT_BUSINESSES(Ldb.BUSINESS, SearchMenuType.RECENT, "BUSINESS SEARCHES"),
  RECENT_DATA(Ldb.DATA, SearchMenuType.RECENT, "RECENT DATA"),
  RECENT_LOCATIONS(Ldb.LOCATION, SearchMenuType.RECENT, "RECENT LOCATIONS"),
  FAVORITE_DATA(Ldb.DATA, SearchMenuType.FAVORITE, "FAVORITE DATA"),
  FAVORITE_LOCATIONS(Ldb.LOCATION, SearchMenuType.FAVORITE, "FAVORITE LOCATIONS"),
  CUSTOR_LOCATIONS(Ldb.LOCATION, null, "CUSTOM LOCATIONS"),
  ;
  
  private final Ldb dataType;
  private final SearchMenuType searchMenuType;
  private final String title;
  
  SearchSubMenuType(Ldb dataType, SearchMenuType searchMenuType, String title) {
    this.dataType = dataType;
    this.searchMenuType = searchMenuType;
    this.title = title;
  }
  
  public Ldb getDataType() {
    return dataType;
  }
  
  @SuppressWarnings("ucd")
  public SearchMenuType getMenuType() {
    return searchMenuType;
  }
  
  public String getTitle() {
    return title;
  }
  
  /**
   * Getting sub menu type by title.
   * @param title title
   * @return sub menu type
   */
  public static SearchSubMenuType getSubMenuTypeByTitle(String title) {
    for (SearchSubMenuType type : SearchSubMenuType.values()) {
      if (title.equals(type.getTitle())) {
        return type;
      }
    }
    throw new AssertionError("No menu type found with title:" + title);
  }
  
}
