package net.simplyanalytics.tests.view.edit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class EditBarChartTests extends TestBase {
  
  private BarChartPage barChartPage;
  private final ViewType view = ViewType.BAR_CHART;
  
  private final List<Location> defaultLocations = new ArrayList<>(
      Arrays.asList(Location.LOS_ANGELES_CA_CITY, Location.USA, Location.CHICAGO_IL_CITY,
          Location.MIAMI_FL_CITY));
  
  /**
   * Signing in, creating new project, add more locations and open the bar chart page.
   */
  @Before
  public void createProjectWithBarChartView() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    
    newViewPage.getActiveView().clickCreate(view).getActiveView().clickDoneButton();
    barChartPage = new BarChartPage(driver);
    
    barChartPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.CHICAGO_IL_CITY);
    barChartPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.MIAMI_FL_CITY);
    
    DataByCategoryDropwDown dataByCategoryBasePanel = ((DataByCategoryPanel) barChartPage
        .getLdbSection().getDataTab().getBrowsePanel())
            .clickOnACategoryData(CategoryData.POPULATION);
    dataByCategoryBasePanel.clickOnADataResult(DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2020);
    barChartPage = (BarChartPage) dataByCategoryBasePanel.clickClose(Page.BAR_CHART);
    
  }
  
  @Test
  public void testEditLocation() {
    
    EditBarChartPage editBarChartPage = (EditBarChartPage) barChartPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editBarChartPage.getActiveView().getLocationsPanel();
    
    verificationStep("Verify that all the locations are selected");
    Assert.assertEquals("The active locations are not the expected",
        defaultLocations.stream().map(location -> location.getName()).collect(Collectors.toList()),
        locationPanel.getSelectedElements());
    
    locationPanel.clickElement(Location.LOS_ANGELES_CA_CITY.getName());
    
    defaultLocations.remove(Location.LOS_ANGELES_CA_CITY);
    
    verificationStep("Verify that the location is unchecked");
    Assert.assertEquals("The active locations are not the expected",
        defaultLocations.stream().map(location -> location.getName()).collect(Collectors.toList()),
        locationPanel.getSelectedElements());
    
    barChartPage = (BarChartPage) editBarChartPage.clickDone();
    
  }
  
  @Test
  public void testNoneLocation() {
    
    EditBarChartPage editBarChartPage = (EditBarChartPage) barChartPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editBarChartPage.getActiveView().getLocationsPanel();
    
    locationPanel.clickElement(Location.LOS_ANGELES_CA_CITY.getName());
    locationPanel.clickElement(Location.USA.getName());
    locationPanel.clickElement(Location.CHICAGO_IL_CITY.getName());
    locationPanel.clickElement(Location.MIAMI_FL_CITY.getName());
    
    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editBarChartPage.getActiveView().isDoneButtonDisabled());
    
    verificationStep("Verify that none of the locations is selected");
    Assert.assertEquals("The active locations are not the expected", new ArrayList<Location>(),
        locationPanel.getSelectedElements());
    
    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.LOCATION_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.LOCATION_MISSING,
        editBarChartPage.getActiveView().getErrorMessage());
  }
  
  @Test
  public void testClearAndSelectAllLocation() {
    
    EditBarChartPage editBarChartPage = (EditBarChartPage) barChartPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editBarChartPage.getActiveView().getLocationsPanel();
    
    locationPanel.clickClear();
    
    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editBarChartPage.getActiveView().isDoneButtonDisabled());
    
    verificationStep("Verify that none of the locations is selected");
    Assert.assertEquals("The active locations are not the expected", new ArrayList<Location>(),
        locationPanel.getSelectedElements());
    
    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.LOCATION_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.LOCATION_MISSING,
        editBarChartPage.getActiveView().getErrorMessage());
    
    locationPanel.clickSelectAll();
    
    verificationStep("Verify that all the location are selected");
    Assert.assertEquals("The active locations are not the expected",
        Arrays.asList(defaultLocations.stream().map(location -> location.getName()).toArray()),
        locationPanel.getSelectedElements());
    
    barChartPage = (BarChartPage) editBarChartPage.clickDone();
    
    verificationStep("Verify that all the locations appear on the Bar Chart table");
    Assert.assertTrue("The locations are incorrect in the table",
        defaultLocations.containsAll(barChartPage.getActiveView().getLocations()));
  }
  
}
