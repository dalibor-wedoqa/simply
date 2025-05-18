package net.simplyanalytics.enums;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Location {
  
  NONE("None", null),
  
  USA("USA", LocationType.USA),
    
  CALIFORNIA("California", LocationType.STATE),
  FLORIDA("Florida", LocationType.STATE),
  NEW_YORK_STATE("New York", LocationType.STATE),

  FRAMINGHAM_MA_CITY("Framingham, MA", LocationType.CITY),
  LOS_ANGELES_CA_CITY("Los Angeles, CA", LocationType.CITY),
  LOS_ANGELES_CA_DMA("Los Angeles, CA ", LocationType.NIELSEN_DESIGNATED_MARKETING_AREA),
  WASHINGTON_DC_CITY("Washington, DC", LocationType.CITY),
  MIAMI_FL_CITY("Miami, FL", LocationType.CITY),
  CHICAGO_IL_CITY("Chicago, IL", LocationType.CITY),
  CLEVELAND_OH_CITY("Cleveland, OH", LocationType.CITY),
  SAN_ANTONIO_CITY("San Antonio, TX", LocationType.CITY),
  LOS_BANOS_CITY("Los Banos, CA", LocationType.CITY),
  LOS_ALTOS_CITY("Los Altos, CA", LocationType.CITY),
  LOS_GATOS_CITY("Los Gatos, CA", LocationType.CITY),
  LOUISIANA_CITY("Louisiana, MO", LocationType.CITY),


  ZIP_90034_LOS_ANGELES("90034, Los Angeles, CA", LocationType.ZIP_CODE),
  ZIP_20001_WASHINGTON_DC("20001, Washington, DC", LocationType.ZIP_CODE),
  ZIP_93505_CALIFORNIA_CITY("93505, California City, CA", LocationType.ZIP_CODE),
  ZIP_44113_CLEVELAND("44113, Cleveland, OH", LocationType.ZIP_CODE),
  
  CONGRESS_DIST_CD07_IL("CD07, IL", LocationType.CONGRESS_DISTRICTS),
  CONGRESS_DIST_CD04_IL("CD04, IL", LocationType.CONGRESS_DISTRICTS),
  CONGRESS_DIST_CD04_CA("CD04, CA", LocationType.CONGRESS_DISTRICTS),
  CONGRESS_DIST_CD23_CA("CD23, CA", LocationType.CONGRESS_DISTRICTS),
  CONGRESS_DIST_CD11_OH("CD11, OH", LocationType.CONGRESS_DISTRICTS),
  
  COUNTY_KERN_COUNTY_CA_CITY("Kern County, CA", LocationType.COUNTY),
  COUNTY_COOK_COUNTY_CHICAGO_IL("Cook County, IL", LocationType.COUNTY),
  COUNTY_MADERA_COUNTY_CA("Madera County, CA", LocationType.COUNTY),
  COUNTY_CUYAHOGA_COUNTY_OH("Cuyahoga County, OH", LocationType.COUNTY),
  COUNTY_POTTAWATTAMIE_IA("Pottawattamie County, IA", LocationType.COUNTY),
  
  CENSUS_TRACT_CT006500_KERN_COUNTY_CA("CT006500, Kern County, CA", LocationType.CENSUS_TRACT),
  CENSUS_TRACT_CT830500_COOK_COUNTY_IL("CT830500, Cook County, IL", LocationType.CENSUS_TRACT),
  CENSUS_TRACT_CT843400_COOK_COUNTY_IL("CT843400, Cook County, IL", LocationType.CENSUS_TRACT),
  CENSUS_TRACT_CT103300_CUYAHOGA_COUNTY_OH("CT103300, Cuyahoga County, OH",
      LocationType.CENSUS_TRACT),
  
  BLOCK_GROUP_BG0065002_KERN_COUNTY_CA("BG0065002, Kern County, CA", LocationType.BLOCK_GROUP),
  BLOCK_GROUP_BG1033001_CUYAHOGA_COUNTY_OH("BG1033001, Cuyahoga County, OH",
      LocationType.BLOCK_GROUP),
  
  RADIUS_LOCATION("Radius Location", LocationType.RADIUS_LOCATION),
  
  // CANADA
  
  TORONTO_ON_CD("Toronto, ON (CD)", LocationType.CENSUS_DIVISION),;
  
  private static Logger logger = LoggerFactory.getLogger(Location.class);
  
  private final String name;
  private final LocationType type;
  
  public String getName() {
    return name;
  }
  
  @SuppressWarnings("ucd")
  public LocationType getType() {
    return type;
  }
  
  Location(String name, LocationType type) {
    this.name = name;
    this.type = type;
  }
  
  public String getNameWithType() {
    return name + " (" + type.getSingularName() + ")";
    
  }
  
  // fields and methods to get enum by name
  private static final Map<String, Location> nameMap;
  
  static {
    nameMap = new HashMap<>();
    for (Location location : Location.values()) {
      nameMap.put(location.getName(), location);
    }
  }
  
  public static Location getByName(String name) {
    Location result = nameMap.get(name);
    if (result == null) {
      logger.warn("Location with name " + name + " not found");
    }
    return result;
  }
  
  @Override
  public String toString() {
    return getNameWithType();
  }
}
