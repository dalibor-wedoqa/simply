package net.simplyanalytics.tests.tabledropdown.headerdropdown.sort;

import java.awt.*;
import java.util.List;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.view.base.HeaderDropdown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.openqa.selenium.Dimension;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BusinessesSortingTests extends TestBase {

  private BusinessesPage businessesPage;

  /**
   * Singning in, creating new project and adding business.
   */
  @Before
  public void login() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    
    mapPage.getLdbSection().clickBusinesses().addRandomBusinesses();

    driver.manage().window().fullscreen();

    businessesPage = ((BusinessesPage) mapPage.getViewChooserSection()
        .clickView(ViewType.BUSINESSES.getDefaultName()));

    while(businessesPage.getToolbar().getNumberOfResults() == 0) {
      businessesPage.getLdbSection().clickBusinesses().addRandomBusinesses();

    }
  }
  
  @Test
  public void testSortingByStreetAddress() {
    String columnName = "Street Address";

    HeaderDropdown headerDropdown = businessesPage.getActiveView()
        .openColumnHeaderDropdown(columnName);
    businessesPage = (BusinessesPage) headerDropdown.clickSortAZ();

    verificationStep("Verify the ascending order of the Street Address values");
    List<List<String>> list = businessesPage.getActiveView().getCellValues(columnName);
    String oldValue = list.get(0).get(0);
    for (List<String> row : list) {
      String newValue = row.get(0);
      Assert.assertTrue("The value " + newValue + " is smaller then the previous value " + oldValue
          + "\n values: " + list, oldValue.compareTo(newValue) <= 0);
      oldValue = newValue;
    }

    headerDropdown = businessesPage.getActiveView().openColumnHeaderDropdown(columnName);
    businessesPage = (BusinessesPage) headerDropdown.clickReverseSort();

    verificationStep("Verify the descending order of the Street Address values");
    list = businessesPage.getActiveView().getCellValues(columnName);
    oldValue = list.get(0).get(0);
    for (List<String> row : list) {
      String newValue = row.get(0);
      Assert.assertTrue("The value " + newValue + " is larger then the previous value " + oldValue,
          oldValue.compareTo(newValue) >= 0);
      oldValue = newValue;
    }
  }
  
  @Test
  @DisplayName("Verify that Business Table view can be sorted by City")
  @Description("The test creates a Buisness Table view, selects the City column header, clicks on Sort A-Z and verifies the sort order. Then it clicks on reverse order and verifies again.")
  @Tag("TCE/Fix")
  //2BDONE
  //Replace the reverse sorting logic
  public void testSortingByCity() {
    String columnName = "City";

    HeaderDropdown headerDropdown = businessesPage.getActiveView()
            .openColumnHeaderDropdown(columnName);
    businessesPage = (BusinessesPage) headerDropdown.clickSortAZ();

    verificationStep("Verify the ascending order of the City values");
    List<List<String>> list = businessesPage.getActiveView().getCellValues(columnName);
    String oldValue = list.get(0).get(0);
    for (List<String> row : list) {
      String newValue = row.get(0);
      Assert.assertTrue("The value " + newValue + " is smaller then the previous value " + oldValue
          + "\n values: " + list, oldValue.compareTo(newValue) <= 0);
      oldValue = newValue;
    }

    headerDropdown = businessesPage.getActiveView().openColumnHeaderDropdown(columnName);
    businessesPage = (BusinessesPage) headerDropdown.clickReverseSort();

    verificationStep("Verify the descending order of the City values");
    list = businessesPage.getActiveView().getCellValues(columnName);
    for (int i = 0; i < list.size() - 1; i++) {
      // Extract the string from each inner list
      String current = list.get(i).get(0);
      String next = list.get(i + 1).get(0);
      // Assert that each element is greater than or equal to the next one
      System.out.println( current +" "+ next);
      Assert.assertTrue("List is not sorted in reverse alphabetical order at index: " + i,
              current.compareTo(next) >= 0);
    }
  }
  
  @Test
  public void testSortingByStateAbbreviation() {
    String columnName = "State Abbreviation";

    HeaderDropdown headerDropdown = businessesPage.getActiveView()
        .openColumnHeaderDropdown(columnName);
    businessesPage = (BusinessesPage) headerDropdown.clickSortAZ();

    verificationStep("Verify the ascending order of the State Abbreviation values");
    List<List<String>> list = businessesPage.getActiveView().getCellValues(columnName);
    String oldValue = list.get(0).get(0);
    for (List<String> row : list) {
      String newValue = row.get(0);
      Assert.assertTrue("The value " + newValue + " is smaller then the previous value " + oldValue
          + "\n values: " + list, oldValue.compareTo(newValue) <= 0);
      oldValue = newValue;
    }

    headerDropdown = businessesPage.getActiveView().openColumnHeaderDropdown(columnName);
    businessesPage = (BusinessesPage) headerDropdown.clickReverseSort();

    verificationStep("Verify the descending order of the State Abbreviation values");
    list = businessesPage.getActiveView().getCellValues(columnName);
    oldValue = list.get(0).get(0);
    for (List<String> row : list) {
      String newValue = row.get(0);
      Assert.assertTrue("The value " + newValue + " is larger then the previous value " + oldValue,
          oldValue.compareTo(newValue) >= 0);
      oldValue = newValue;
    }
  }

  @Test
  public void testSortingByZipCode() {
    String columnName = "Zip Code";

    HeaderDropdown headerDropdown = businessesPage.getActiveView()
        .openColumnHeaderDropdown(columnName);
    businessesPage = (BusinessesPage) headerDropdown.clickSortAZ();

    sleep(1000);
    
    verificationStep("Verify the ascending order of the Zip Code values");
    List<List<String>> list = businessesPage.getActiveView().getCellValues(columnName);
    String oldValue = list.get(0).get(0);
    for (List<String> row : list) {
      String newValue = row.get(0);
      Assert.assertTrue("The value " + newValue + " is smaller than the previous value " + oldValue,
          oldValue.compareTo(newValue) <= 0);
      oldValue = newValue;
    }

    headerDropdown = businessesPage.getActiveView().openColumnHeaderDropdown(columnName);
    businessesPage = (BusinessesPage) headerDropdown.clickReverseSort();
    
    sleep(1000);

    verificationStep("Verify the descending order of the Zip Code values");
    list = businessesPage.getActiveView().getCellValues(columnName);
    oldValue = list.get(0).get(0);
    for (List<String> row : list) {
      String newValue = row.get(0);
      Assert.assertTrue("The value " + newValue + " is larger than the previous value " + oldValue,
          oldValue.compareTo(newValue) >= 0);
      oldValue = newValue;
    }
  }
    
  @Test
  public void testSortingByTelephoneNumber() {
    String columnName = "Telephone Number";

    if(businessesPage.getToolbar().getNumberOfResults() > 1) {
      HeaderDropdown headerDropdown = businessesPage.getActiveView()
          .openColumnHeaderDropdownTrick(columnName);
      businessesPage = (BusinessesPage) headerDropdown.clickSortAZ();
  
      verificationStep("Verify the ascending order of the Telephone Number values");
      List<List<String>> list = businessesPage.getActiveView().getCellValues(columnName);
      long oldValue = 0;
      if(!list.get(0).get(0).contentEquals("")) {
        oldValue = Long.parseLong(list.get(0).get(0));
      }
      else {
        oldValue = Long.parseLong("9999999999");
      }
      for (List<String> row : list) {
        long newValue = 0;
        if(!row.get(0).contentEquals("")) {
          newValue = Long.parseLong(row.get(0));
        }
        else {
          oldValue = Long.parseLong("9999999999");
        }
        Assert.assertTrue("The value " + newValue + " is smaller than the previous value " + oldValue,
            newValue >= oldValue);
        oldValue = newValue;
      }
  
      headerDropdown = businessesPage.getActiveView().openColumnHeaderDropdownTrick(columnName);
      businessesPage = (BusinessesPage) headerDropdown.clickReverseSort();
  
      verificationStep("Verify the descending order of the Telephone Number values");
      list = businessesPage.getActiveView().getCellValues(columnName);
      
      oldValue = 0;
      if(!list.get(0).get(0).contentEquals("")) {
        oldValue = Long.parseLong(list.get(0).get(0));
      }
      else {
        oldValue = Long.parseLong("9999999999");
      }
      
      for (List<String> row : list) {
        long newValue = 0;
        if(!row.get(0).contentEquals("")) {
          newValue = Long.parseLong(row.get(0));
        }

        Assert.assertTrue("The value " + newValue + " is larger than the previous value " + oldValue,
            newValue <= oldValue);
        oldValue = newValue;
      }
    }
    else {
      logger.info("Number of result is: " + businessesPage.getToolbar().getNumberOfResults());
    }
  }
  
  @Test
  @DisplayName("Verify that Business Table rows can be sorted by latitude")
  @Description("The test creates a Business Table view. Then it adds businesses and sorts them by latitude and checks if the sorting is right.")
  public void testSortingByLatitude() {
    String columnName = "Latitude";
    HeaderDropdown headerDropdown = businessesPage.getActiveView()
            .openColumnHeaderDropdownTrick(columnName);
    businessesPage = (BusinessesPage) headerDropdown.clickSortAZ();

    verificationStep("Verify the ascending order of the Latitude values");
    List<List<String>> list = businessesPage.getActiveView().getCellValues(columnName);
    Double oldValue = Double.parseDouble(list.get(0).get(0));
    for (List<String> row : list) {
      Double newValue = Double.parseDouble(row.get(0));
      Assert.assertTrue("The value " + newValue + " is smaller than the previous value " + oldValue,
          newValue >= oldValue);
      oldValue = newValue;
    }

    headerDropdown = businessesPage.getActiveView()
            .openColumnHeaderDropdownTrick(columnName);
    businessesPage = (BusinessesPage) headerDropdown.clickReverseSort();

    verificationStep("Verify the descending order of the Latitude values");
    list = businessesPage.getActiveView().getCellValues(columnName);
    oldValue = Double.parseDouble(list.get(0).get(0));
    for (List<String> row : list) {
      Double newValue = Double.parseDouble(row.get(0));
      Assert.assertTrue("The value " + newValue + " is larger than the previous value " + oldValue,
          newValue <= oldValue);
      oldValue = newValue;
    }
  }
  
  @Test
  @DisplayName("Verify that Business Table rows can be sorted by longitude")
  @Description("The test creates a Business Table view. Then it adds businesses and sorts them by longitude and checks if the sorting is right.")
  @Tag("TCE/Fix")
  public void testSortingByLongitude() {
    String columnName = "Longitude";

    HeaderDropdown headerDropdown = businessesPage.getActiveView()
        .openColumnHeaderDropdown(columnName);
    businessesPage = (BusinessesPage) headerDropdown.clickSortAZ();

    verificationStep("Verify the ascending order of the Longitude values");
    List<List<String>> list = businessesPage.getActiveView().getCellValues(columnName);
    Double oldValue = Double.parseDouble(list.get(0).get(0));
    for (List<String> row : list) {
      Double newValue = Double.parseDouble(row.get(0));
      Assert.assertTrue("The value " + newValue + " is smaller than the previous value " + oldValue,
          newValue >= oldValue);
      oldValue = newValue;
    }

    headerDropdown = businessesPage.getActiveView().openColumnHeaderDropdown(columnName);
    businessesPage = (BusinessesPage) headerDropdown.clickReverseSort();

    verificationStep("Verify the descending order of the Longitude values");
    list = businessesPage.getActiveView().getCellValues(columnName);
    oldValue = Double.parseDouble(list.get(0).get(0));
    for (List<String> row : list) {
      Double newValue = Double.parseDouble(row.get(0));
      Assert.assertTrue("The value " + newValue + " is larger than the previous value " + oldValue,
          newValue <= oldValue);
      oldValue = newValue;
    }
  }

  @Test
  public void testSortingByPrimaryNaics() {
    String columnName = "Primary NAICS";
    System.out.println("Starting test: Sorting by " + columnName);

    // Open the column header dropdown for sorting
    HeaderDropdown headerDropdown = businessesPage.getActiveView()
            .openColumnHeaderDropdownTry(columnName);
    System.out.println("Opened column header dropdown for " + columnName);

    // Click to sort in ascending order
    businessesPage = (BusinessesPage) headerDropdown.clickSortAZ();
    System.out.println("Clicked Sort A-Z to sort " + columnName + " in ascending order");

    // Verify ascending order of the column values
    verificationStep("Verify the ascending order of the Primary NAICS values");
    List<List<String>> list = businessesPage.getActiveView().getCellValues(columnName);
    System.out.println("Fetched values from the column: " + columnName);

    long oldValue = Long.parseLong(list.get(0).get(0));
    System.out.println("Initial value: " + oldValue);

    for (List<String> row : list) {
      long newValue = Long.parseLong(row.get(0));
      System.out.println("Comparing values - Previous: " + oldValue + ", Current: " + newValue);

      Assert.assertTrue("The value " + newValue + " is smaller than the previous value " + oldValue,
              newValue >= oldValue);
      oldValue = newValue;
    }
    System.out.println("Verified sorting in ascending order successfully.");

    // Open the dropdown again for reverse sorting
    headerDropdown = businessesPage.getActiveView().openColumnHeaderDropdownTry(columnName);
    System.out.println("Reopened column header dropdown for " + columnName);

    // Click to sort in descending order
    businessesPage = (BusinessesPage) headerDropdown.clickReverseSort();
    System.out.println("Clicked Reverse Sort to sort " + columnName + " in descending order");

    // Verify descending order of the column values
    verificationStep("Verify the descending order of the " + columnName + " values");
    list = businessesPage.getActiveView().getCellValues(columnName);
    System.out.println("Fetched values from the column again for verification.");

    oldValue = Long.parseLong(list.get(0).get(0));
    System.out.println("Initial value after reverse sort: " + oldValue);

    for (List<String> row : list) {
      long newValue = Long.parseLong(row.get(0));
      System.out.println("Comparing values - Previous: " + oldValue + ", Current: " + newValue);

      Assert.assertTrue("The value " + newValue + " is larger than the previous value " + oldValue,
              newValue <= oldValue);
      oldValue = newValue;
    }
    System.out.println("Verified sorting in descending order successfully.");

    System.out.println("Test Sorting by Primary NAICS completed.");
  }

}