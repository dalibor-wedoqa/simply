package net.simplyanalytics.enums;

public enum RankingExportOptions {

  EXPORT_TOP_10_ROWS("Export Top 10 Rows"),
  EXPORT_TOP_100_ROWS("Export Top 100 Rows"),
  EXPORT_TOP_1000_ROWS("Export Top 1000 Rows"),
  EXPORT_TOP_10000_ROWS("Export Top 10000 Rows"),
  EXPORT_ALL_ROWS("Export All Rows");
  
  private String name;
  
  private RankingExportOptions(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
}
