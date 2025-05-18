package net.simplyanalytics.enums;

public enum DataPackage {

  STANDARD_DATA_PACKAGE("Standard Data Package"),
  PREMIUM_DATA("Premium Data"),
 // the EASI is no longer available EASI("EASI Data Package")
  ;
  
  private final String packageName;
  
  DataPackage(String packageName) {
    this.packageName = packageName;
  }

  public String getPackageName() {
    return packageName;
  }
  
  public static DataPackage getDataPackageByName(String name) {
    for(DataPackage dataPackage : DataPackage.values()) {
      if(dataPackage.getPackageName().toUpperCase().equals(name))
        return dataPackage;
    }
    throw new AssertionError("Datapackage by name: " + name + " is not supported");
  }
  
}
