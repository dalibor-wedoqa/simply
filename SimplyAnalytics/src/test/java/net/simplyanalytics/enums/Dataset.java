package net.simplyanalytics.enums;

import java.util.ArrayList;
import java.util.List;

public enum Dataset {
  
  //USA datasets
  COMMUNITY_DEMOGRAPHICS("Community Demographics", "USACSSUB", "Community Demographics", "dem", "USA","Latest"),
  CONSUMER_EXPENDITURE_ESTIMATE("Consumer Expenditure Estimates", "USCEX", "Consumer Expenditure Estimates", "cex", "USA","Latest"),
  US_ELECTION_DATA("US Election Data","USELEC","US Election Data","elec","USA","Latest"),

  DECENNIAL_CENSUS_2000("2000 Decennial Census", "USDEC2000", "2000 Decennial Census", "cen 2000", "USA",""),
  DECENNIAL_CENSUS_2010("2010 Decennial Census", "USDEC2010", "2010 Decennial Census", "cen 2010", "USA",""),
  DECENNIAL_CENSUS_2020("2020 Decennial Census", "USDEC2020", "2020 Decennial Census", "cen 2020", "USA","Latest"),
  //DECENNIAL_CENSUS_2020_NHVIEW("2020 Decennial Census", "USDEC2020", "2020 Decennial Census", "cen", "USA","Latest"),
  AMERICAN_COMMUNITY_SURVEY("American Community Survey", "USACS", "American Community Survey US", "acs", "USA","Latest"),
  COUNTY_BUSINESS_PATTERS_SUMMARY("County Business Patterns Summary", "USCBPSUM", "US County Business Patterns", "cbp", "USA","Latest"),
  
  UNIFORM_CRIME_REPORTING_US("Uniform Crime Reporting", "UCR", "Uniform Crime Report", "ucr", "USA","Latest"),
  
  CLIMATE_DIVISIONAL_DATABASE("Climate Divisional Database", "USNOAA", "NOAA Weather Data", "noaa", "USA","Latest"),

 // EASI_HEALTH_US("EASI Health", "HEALTH", "EASI Health Care", "easi", "USA"),
 // LIFE_STAGE_CLUSTERS_US("Life Stage Clusters", "LFSTG", "Life Stages", "easi", "USA"),
 // MRI_CONSUMER_SURVEY_US("MRI Consumer Survey", "MRI", "MRI Consumer Survey", "easi","USA"), these datasets are no longer available

  AGS_CENSUS_US("AGS Census", "AGSCEN", "Census Data", "ags", "USA","Latest"),
  AGS_HEALTH_CARE_US("AGS Health Care", "AGSHEALTH", "AGS Health Care", "ags", "USA","Latest"),

  AGS_2000_SUPPLEMENTAL_US("AGS 2000 Supplemental", "AGSUP00", "Historical Census", "ags", "USA",""),
  AGS_2010_SUPPLEMENTAL_US("AGS 2010 Supplemental", "AGSUP10", "Historical Census", "ags", "USA",""),
  AGS_CENSUS_1980_US("AGS Census 1980", "AGSCEN80", "Historical Census", "ags","USA",""),
  AGS_CENSUS_1990_US("AGS Census 1990", "AGSCEN90", "Historical Census", "ags", "USA",""),
  AGS_CENSUS_2000_US("AGS Census 2000", "AGSCEN2000", "Historical Census", "ags", "USA",""),


  PRIZM_Nielsen_PREMIER_US("PRIZM Premier", "PRZMPUS", "Claritas PRIZM Premier", "prizm", "USA","Latest"),
  CONNEXIONS_US("ConneXions", "CONNX", "ConneXions", "connexions","USA","Latest"),
  CONSUMER_BUYING_POWER_US("Consumer Buying Power", "CNBYPW", "Consumer Buying Power", "cbp", "USA","Latest"),
  FINANCIAL_CLOUT_US("Financial CLOUT", "FINCLT", "Financial CLOUT", "clout", "USA","Latest"),
  PSYCLE_PREMIER_US("P$YCLE Premier", "PSYCLEP", "P$YCLE Premier", "p$ycle", "USA","Latest"),
  RETAIL_MARKET_POWER_US("Retail Market Power", "RTMKPW", "Retail Market Power", "rmp", "USA","Latest"),

  PRIZM_US_RETIRED_2017("PRIZM (Retired 2017)", "PRZMUS", "Nielsen PRIZM", "prizm", "USA",""),
  PSYCLE_US("P$YCLE", "PSYCLE", "P$YCLE", null, "USA",""),

  SIMMONS_LOCAL_US("MRI-SimmonsLOCAL", "SMN", "MRI-SimmonsLOCAL", "simmons","USA","Latest"),

  SIMMONS_NATIONAL_CONSUMER_SURVEY("MRI-Simmons National Consumer Survey", "SMNNCS", null, null, "USA",""),

  SCARBOROUGH_LOCAL_INSIGHTS("Scarborough Local Insights", "SCRBGH", "Nielsen Scarborough", "scarborough","USA","Latest"),

  NIELSEN_SCARBOROUGH_CROSSTAB_US("Nielsen Scarborough Crosstabs", "SCRBGHXTAB", null, null, "USA",""),
  COVID_19_OUTBREAK_USA_2020("COVID-19 Outbreak", "COVID19", "COVID-19 Outbreak - USA 2020", null, "USA",""),
  HISTORICAL_ATLANTA_REGIONAL_COMMISION_DATE("Historical Atlanta Regional Commission Data","GSU", "Historical Atlanta Data", "", "USA",""),
  COUNTY_BUSINESS_PATTERNS_SUMMARY("County Business Patterns Summary","USACS",null, null, "USA",""),
  
//  BUSINESS_COUNTS_US("Business Counts", "NAICS", "Business Counts", "easi", "USA"),
//  CENSUS_US("Census", "CENSUS", "Census Data", "easi", "USA"),
 // CONSUMER_EXPENDITURE_US("Consumer Expenditure", "CONEXP", "Consumer Expenditure", "easi", "USA"),
 // OTHER_US("Other", "OTHR", null, "easi", "USA"),
 // PROFILES_US("Profiles", "PROF", null, "easi", "USA"),
 // RETAIL_SALES_US("Retail Sales", "RET", "Retail Sales", "easi", "USA"),
  
  //Canadian datasets
  //Standard Data Package
  ADJUSTED_CENSUS_2001_CA("Adjusted Census 2001", "ADJCEN01", "", null, "Canada",""),
  ADJUSTED_CENSUS_2006_CA("Adjusted Census 2006", "ADJCEN06", "", null, "Canada",""),
  ADJUSTED_CENSUS_20011_CA("Adjusted Census 2011", "ADJCEN11", "", null, "Canada",""),
  ADJUSTED_CENSUS_2016_CA("Adjusted Census 2016", "ADJCEN16", "CensusPlus 2016", null, "Canada",""),
  ADJUSTED_CENSUS_2021_CA("Adjusted Census 2021 CA", "ADJCEN21", "CensusPlus 2021", null, "Canada","Latest"),
  DEMOGRAPHIC_ESTIMATES_CA("Demographic Estimates", "DEMEST", "Demographic Estimates", null, "Canada","Latest"),
  DEMOSTATS_TRENDS_CA("DemoStats Trends", "DEMESTTR", "Demographic Trends", null, "Canada","Latest"),
  HOUSEHOLD_SPENDING_CA("Household Spending", "HSPND", "Household Spending", null, "Canada","Latest"),

  COVID_19_OUTBREAK_CANADA_2020("COVID-19 Outbreak", "COVID19CA", "COVID-19 Outbreak - Canada 2020", null, "Canada",""),

  //Premium Data
  AGE_BY_INCOME_CA("Age by Income", "ABI", "Age By Income", null, "Canada","Latest"),
  CANADIAN_COMMUNITY_HEALTH_SURVEY("Canadian Community Health Survey", "CACCHSG2", "Canadian Community Health Survey", null, "Canada","Latest"),
  CANADIAN_COMMUNITY_HEALTH_SURVEY_2014_2019("Canadian Community Health Survey (2014-2019)", "CCHS", "Canadian Community Health Survey", null, "Canada",""),
  DAYTIME_POPULATION_CA("Daytime Population", "DAYPOP", "Daytime Population", null, "Canada","Latest"),
  DIGITAL_LIFESTYLE_CA("Digital Lifestyle", "DGTLS", "Digital Lifestyle", null, "Canada","Latest"),
  FOODSPEND_CA("FoodSpend", "FSPND", "Food Spending", null, "Canada","Latest"),
  GAYBOURHOODS_CA("Gaybourhoods", "GAYBRH", "Food Spending", null, "Canada",""),
  NUMERIS_OPTICKS_PERSONAL_CARE_CA("Numeris Opticks - Personal Care", "OPTCARE", "Numeris Opticks", null, "Canada",""),
  PRIZM_CA("PRIZM", "CAPRIZMG4", "PRIZM", null, "Canada","Latest"),
  PRIZM_CA_SUBSET_AB_DATA("PRIZM - AB", "CAPRIZMG4AB", "PRIZM", null, "Canada","Latest"),
  PRIZM_CA_SUBSET_BC_DATA("PRIZM - BC", "CAPRIZMG4BC", "PRIZM", null, "Canada","Latest"),
  PRIZM_CA_SUBSET_ON_DATA("PRIZM - ON", "CAPRIZMG4ON", "PRIZM", null, "Canada","Latest"),
  PRIZM5_CA("PRIZM5 (2015-2019)", "PRZM5CA", "PRIZM5", null, "Canada",""),
  PRIZM5_CA_SUBSET_AB("PRIZM5 (2015-2019) - AB", "PRZM5ABCA", "PRIZM5", null, "Canada",""),
  PRIZM5_CA_SUBSET_BC("PRIZM5 (2015-2019) - BC", "PRZM5BCCA", "PRIZM5", null, "Canada",""),
  PRIZM5_CA_SUBSET_ON("PRIZM5 (2015-2019) - ON", "PRZM5ONCA", "PRIZM5", null, "Canada","");
  
  private String datasetName;
  private String id;
  private String rootName;
  private String badge;
  private String state;

  private String release;
  
  Dataset(String datasetName, String vendor, String rootName, String badge, String state, String release) {
    this.datasetName = datasetName;
    this.id = vendor;
    this.rootName = rootName;
    this.badge = badge;
    this.state = state;
    this.release = release;
  }
  
  public String getDatasetName() {
    return datasetName;
  }
  
  public String getId() {
    return id;
  }
  
  public String getRootName() {
    return rootName;
  }
  
  public String getBadge() {
    return badge;
  }
  
  public String getState() {
    return state;
  }

  public String getRelease(){return release;}
  
  @Override
  public String toString() {
    return getId();
  }
  
  public static Dataset getDatasetByName(String name) {
    for(Dataset dataset : Dataset.values()) {
      if(dataset.getDatasetName().equals(name))
        return dataset;
    }
    throw new AssertionError("Dataset by name: " + name + " is not supported");
  }
  
  public static Dataset getCanadianDatasetByName(String name) {
    for(Dataset dataset : Dataset.values()) {
      if(dataset.getDatasetName().equals(name) && (dataset.getState().equals("Canada")))
        return dataset;
    }
    throw new AssertionError("Dataset by name: " + name + " is not supported");
  }
  
  public static List<Dataset> getAllUsaDatasets() {
    List<Dataset> usaDatasetList = new ArrayList<Dataset>();
    for(Dataset dataset : Dataset.values()) {
      if(dataset.getState().equals("USA"))
        usaDatasetList.add(dataset);
    }
    return usaDatasetList;
  }
  
  public static List<Dataset> getAllCanadianDatasets() {
    List<Dataset> canadianDatasetList = new ArrayList<Dataset>();
    for(Dataset dataset : Dataset.values()) {
      if(dataset.getState().equals("Canada"))
        canadianDatasetList.add(dataset);
    }
    return canadianDatasetList;
  }

  public static List<Dataset> getUsaDatasetByRelease(String release){
    List<Dataset> usaDatasetList = new ArrayList<Dataset>();
    for(Dataset dataset : Dataset.values()) {
      if(dataset.getState().equals("USA") && dataset.getRelease().equals(release))
        usaDatasetList.add(dataset);
    }
    return usaDatasetList;
  }

  public static List<Dataset> getCanadaDatasetByRelease(String release){
    List<Dataset> usaDatasetList = new ArrayList<Dataset>();
    for(Dataset dataset : Dataset.values()) {
      if(dataset.getState().equals("Canada") && dataset.getRelease().equals(release))
        usaDatasetList.add(dataset);
    }
    return usaDatasetList;
  }

}
