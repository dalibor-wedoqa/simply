package net.simplyanalytics.enums;

@SuppressWarnings("ucd")
public enum Page {
  
  MANAGE_PROJECT("Manage Project Page"),
  NEW_VIEW("New View"),
  
  MAP_VIEW("Map Page"),
  EDIT_MAP("Edit Map"),
  QUICK_REPORT("Quick Report"),
  COMPARISON_REPORT_VIEW("Comparison Report Page"),
  BUSINESSES_VIEW("Businesses Page"),
  RANKING_VIEW("Ranking Page"),
  RING_STUDY_VIEW("Ring Study Page"),
  RELATED_DATA_VIEW("Related Data Page"),
  TIME_SERIES_VIEW("Time Series Page"),
  BAR_CHART("Bar Chart Page"),
  SIMMONS_CROSSTAB_PAGE("Simmons Crosstab Page"),
  EDIT_SIMMONS_CROSSTAB_PAGE("Edit Simmons Crosstab"),
  SCATTER_PLOT("Scatter Plot"),
  SCARBOROUGH_CROSSTAB_PAGE("Scarborough Crosstab Table"),
  EDIT_SCARBOROUGH_CROSSTAB_PAGE("Edit Scarborough Crosstab"),
  HISTOGRAM("Histogram");
  
  private final String viewPageName;
  
  Page(String viewPageName) {
    this.viewPageName = viewPageName;
  }
  
  public String getViewPageName() {
    return viewPageName;
  }
  
}
