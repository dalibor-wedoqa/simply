package net.simplyanalytics.enums;

public enum CategoryData {
  
  MY_IMPORTED_DATA("My Imported Data", "imports"),
  AGE("Age","age"),
  ANCESTRY("Ancestry", "ancestry"),
  EDUCATION("Education", "education"),
  FAMILY_TYPE("Family Type & Marital Status", "family_type"),
  FINANCE("Finance", "finance"),
  GENDER("Gender", "gender"),
  POPULAR_DATA("Popular Data", "popular_data"),
  POPULATION("Population", "population"),
  RACE_AND_ETHNICITY("Race & Ethnicity", "race_ethnicity"),
  RETAIL("Retail", "retail"),
  POVERTY("Poverty", "poverty"),
  INCOME("Income", "income"),
  JOBS_AND_EMPLOYMENT("Jobs & Employment", "jobs_employment"),
  LANGUAGE("Language", "language"),
  HOUSEHOLDS("Households", "households"),
  HOUSING("Housing", "housing"),
  HEALTH("Health", "health"),
  CONSUMER_BEHAVIOR("Consumer Behavior", "consumer_behavior"),
  TECHNOLOGY("Technology", "technology"),
  ;
  
  String name;
  String dataId;
  
  CategoryData(String name, String dataId) {
    this.name = name;
    this.dataId = dataId;
  }
  
  public String getDataId() {
    return dataId;
  }
  
  public String getName() {
    return name;
  }
  
  @Override
  public String toString() {
    return getName();
  }
}
