package net.simplyanalytics.constants;

public enum Emails {
  
  ACTIVATION("Welcome to SimplyAnalytics - Please Activate Account"),
  DATA_EXPORTED("Data exported from SimplyAnalytics"),
  RESET_PASSWORD("SimplyAnalytics Password Assistance"),
  CHANGE_EMAIL_ADDRESS("SimplyAnalytics Email Address Update Confirmation"),
  ;
  
  private String title;
  
  Emails(String title) {
    this.title = title;
  }
  
  public String getTitle() {
    return title;
  }
  
}
