package net.simplyanalytics.enums;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BusinessSearchBy {
  
  NAME("Name"),
  SIC("SIC"),
  NAICS("NAICS"),
  NUMBER_OF_LOCAL_EMPLOYEES("# of local employees"),
  SALES_VOLUME("Sales volume"),
  ;
  
  private String name;
  
  BusinessSearchBy(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  private static Map<String, BusinessSearchBy> byName = Stream.of(values())
      .collect(Collectors.toMap(BusinessSearchBy::getName, Function.identity()));
  /**
   * Getting business by name.
   * @param name business name
   * @return business
   */
  public static BusinessSearchBy getByName(String name) {
    BusinessSearchBy match = byName.get(name);
    if (byName == null) {
      throw new AssertionError("No search by found with name: " + name);
    }
    return match;
  }
  
  @Override
  public String toString() {
    return getName();
  }
}
