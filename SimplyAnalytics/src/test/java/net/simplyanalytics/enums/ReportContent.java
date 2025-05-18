package net.simplyanalytics.enums;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ReportContent {
  
  PRIZM_REPORT("PRIZM Report"),
  DEMOGRAPHIC_OVERVIEW("Demographic Overview"),
  HOUSING_DETAIL("Housing Detail"),
  CEX_SPOTLIGHT("CEX Spotlight"),
  ;
  
  private static Logger logger = LoggerFactory.getLogger(Location.class);
  
  private final String name;
  
  public String getName() {
    return name;
  }
  
  ReportContent(String name) {
    this.name = name;
  }
  
  // fields and methods to get enum by name
  private static final Map<String, ReportContent> nameMap;
  
  static {
    nameMap = new HashMap<>();
    for (ReportContent reportContent : ReportContent.values()) {
      nameMap.put(reportContent.getName(), reportContent);
    }
  }
  
  public static ReportContent getByName(String name) {
    ReportContent result = nameMap.get(name);
    if (result == null) {
      logger.warn("Report content with name " + name + " not found");
    }
    return result;
  }
}
