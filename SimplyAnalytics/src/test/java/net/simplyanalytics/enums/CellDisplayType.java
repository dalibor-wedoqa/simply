package net.simplyanalytics.enums;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum CellDisplayType {
  VERTICAL("Vertical %, Index, Horizontal %"),
  SAMPLE("Sample, Weighted (000), Total %");
  
  private static Logger logger = LoggerFactory.getLogger(CellDisplayType.class);
  
  private final String name;

  CellDisplayType(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  // fields and methods to get enum by name
  private static final Map<String, CellDisplayType> nameMap;
  static {
    nameMap = new HashMap<>();
    for (CellDisplayType displayType : CellDisplayType.values()) {
      nameMap.put(displayType.getName(), displayType);
    }
  }
  
  public static CellDisplayType getByName(String name) {
    CellDisplayType result = nameMap.get(name);
    if (result == null) {
      logger.warn("DisplayType with name " + name + " not found");
    }
    return result;
  }
  
}
