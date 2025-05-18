package net.simplyanalytics.enums;

import java.util.HashMap;
import java.util.Map;

public enum LocationType {

  //USA
  USA("USA", "USA"),
  STATE("State", "States"),
  COUNTY("County", "Counties"),
  CITY("City", "Cities"),
  ZIP_CODE("Zip Code", "Zip Codes"),
  CENSUS_TRACT("Census Tract", "Census Tracts"),
  BLOCK_GROUP("Block Group", "Block Groups"),
  RADIUS_LOCATION("Radius Location", "Radius Location"),
  
  REGIONS("Region", "Regions"),
  DIVISIONS("Division", "Divisions"),
  NIELSEN_DESIGNATED_MARKETING_AREA("Nielsen Designated Marketing Area", "Nielsen Designated Marketing Area"),
  CORE_BASED_STATISTICAL_AREAS("Core-based Statistical Area", "Core-based Statistical Areas"),
  CONGRESS_DISTRICTS("Congress. Dist.", "Congress. Dist."),
  STATE_UPPER_DISTRICTS("State Upper District", "State Upper Districts"),
  STATE_LOWER_DISTRICTS("State Lower District", "State Lower Districts"),
  SECONDARY_SCHOOL_DISTRICTS("Secondary School District", "Secondary School Districts"),
  ELEMENTARY_SCHOOL_DISTRICTS("Elementary School District", "Elementary School Districts"),
  
  // CANADA
  CANADA("Canada", "Canada"),
  PROVINCE("Province", "Provinces"),
  CENSUS_METRO_AREA("Census Metro Area", "Census Metro Areas"),
  CENSUS_DIVISION("Census Division", "Census Divisions"),
  FORWARD_SORTATION_AREA("Forward Sortation Area", "Forward Sortation Areas"),
  CENSUS_SUBDIVISION("Census Subdivision", "Census Subdivisions"),
  DISSEMINATION_AREA("Dissemination Area", "Dissemination Areas");
  
  private final String singularName;
  private final String pluralName;
  
  public String getSingularName() {
    return singularName;
  }
  
  public String getPluralName() {
    return pluralName;
  }
  
  LocationType(String singularName, String pluralName) {
    this.singularName = singularName;
    this.pluralName = pluralName;
  }
  
  // fields and methods to get enum by singular name
  private static final Map<String, LocationType> singularMap;
  
  static {
    singularMap = new HashMap<>();
    for (LocationType viewBy : LocationType.values()) {
      singularMap.put(viewBy.getSingularName(), viewBy);
    }
  }
  
  @SuppressWarnings("ucd")
  public static LocationType getBySingularName(String name) {
    return singularMap.get(name);
  }
  
  // fields and methods to get enum by plural name
  private static final Map<String, LocationType> pluralMap;
  
  static {
    pluralMap = new HashMap<>();
    for (LocationType viewBy : LocationType.values()) {
      pluralMap.put(viewBy.getPluralName(), viewBy);
    }
  }
  
  public static LocationType getByPluralName(String name) {
    return pluralMap.get(name);
  }
  
  @Override
  public String toString() {
    return pluralName;
  }
}
