package net.simplyanalytics.tests.view.edit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import net.simplyanalytics.pageobjects.pages.main.editviews.EditComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EditComparisonReportTests extends TestBase {

  private ComparisonReportPage comparisonReportPage;
  private final ViewType view = ViewType.COMPARISON_REPORT;

  private final List<Location> DEFAULT_LOCATIONS = new ArrayList<>(
      Arrays.asList(Location.LOS_ANGELES_CA_CITY, Location.USA, Location.CHICAGO_IL_CITY,
          Location.MIAMI_FL_CITY));
  
  
  private final List<DataVariable> expectedVariables = new ArrayList<>(
		  Arrays.asList(
		  DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024,
		  DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024,
		  DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024));
	
  /**
   * Signing in and creating new project. Open comparison report page. Add more
   * locations to compare.
   */
  @Before
  public void createProjectWithComparisonReportView() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    comparisonReportPage = (ComparisonReportPage) mapPage.getViewChooserSection()
        .clickView(ViewType.COMPARISON_REPORT.getDefaultName());
    comparisonReportPage.getLdbSection().getLocationsTab()
        .voidChooseLocation(Location.CHICAGO_IL_CITY);
    comparisonReportPage.getLdbSection().getLocationsTab()
        .voidChooseLocation(Location.MIAMI_FL_CITY);

    DataByCategoryDropwDown basePanel = ((DataByCategoryPanel) comparisonReportPage.getLdbSection()
        .getDataTab().getBrowsePanel()).clickOnACategoryData(CategoryData.POPULATION);
    basePanel.clickOnADataResult(DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024);
    basePanel.clickClose(Page.COMPARISON_REPORT_VIEW);

  }

  @Test
  public void testEditLocation() {
    // Verify default values
    verificationStep("Verify that the initial locations are present in the comparison table");
    Assert.assertEquals("The default locations are incorrect in the table", DEFAULT_LOCATIONS,
        comparisonReportPage.getActiveView().getLocations());

    EditComparisonReportPage editComparisonReportPage = (EditComparisonReportPage) 
        comparisonReportPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editComparisonReportPage.getActiveView()
        .getLocationsPanel();

    verificationStep("Verify that all the locations are selected");
    Assert.assertEquals("The active locations are not the expected",
        DEFAULT_LOCATIONS.stream().map(location -> location.getName()).collect(Collectors.toList()),
        locationPanel.getSelectedElements());

    locationPanel.clickElement(Location.LOS_ANGELES_CA_CITY.getName());

    DEFAULT_LOCATIONS.remove(Location.LOS_ANGELES_CA_CITY);

    verificationStep("Verify that the location is unchecked");
    Assert.assertEquals("The active locations are not the expected",
        DEFAULT_LOCATIONS.stream().map(location -> location.getName()).collect(Collectors.toList()),
        locationPanel.getSelectedElements());

    comparisonReportPage = (ComparisonReportPage) editComparisonReportPage.clickDone();

    verificationStep("Verify that the expected location are present in the comparison table");
    Assert.assertEquals("The locations are incorrect in the table", DEFAULT_LOCATIONS,
        comparisonReportPage.getActiveView().getLocations());
  }

  @Test
  public void testNoneLocation() {

    EditComparisonReportPage editComparisonReportPage = (EditComparisonReportPage) 
        comparisonReportPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editComparisonReportPage.getActiveView()
        .getLocationsPanel();

    locationPanel.clickElement(Location.LOS_ANGELES_CA_CITY.getName());
    locationPanel.clickElement(Location.USA.getName());
    locationPanel.clickElement(Location.CHICAGO_IL_CITY.getName());
    locationPanel.clickElement(Location.MIAMI_FL_CITY.getName());

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editComparisonReportPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the locations is selected");
    Assert.assertEquals("The active locations are not the expected", new ArrayList<Location>(),
        locationPanel.getSelectedElements());

    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.LOCATION_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.LOCATION_MISSING,
        editComparisonReportPage.getActiveView().getErrorMessage());
  }

  @Test
  public void testClearAndSelectAllLocation() {
    EditComparisonReportPage editComparisonReportPage = 
        (EditComparisonReportPage) comparisonReportPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editComparisonReportPage.getActiveView()
        .getLocationsPanel();

    locationPanel.clickClear();

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editComparisonReportPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the locations is selected");
    Assert.assertEquals("The active locations are not the expected", new ArrayList<Location>(),
        locationPanel.getSelectedElements());

    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.LOCATION_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.LOCATION_MISSING,
        editComparisonReportPage.getActiveView().getErrorMessage());

    locationPanel.clickSelectAll();

    verificationStep("Verify that all the location are selected");
    Assert.assertEquals("The active locations are not the expected",
        DEFAULT_LOCATIONS.stream().map(location -> location.getName()).collect(Collectors.toList()),
        locationPanel.getSelectedElements());

    comparisonReportPage = (ComparisonReportPage) editComparisonReportPage.clickDone();

    verificationStep("Verify that all the locations appear in the comparison table");
    Assert.assertEquals("The locations are incorrect in the table", DEFAULT_LOCATIONS,
        comparisonReportPage.getActiveView().getLocations());
  }

  @Test
  public void testEditVariable() {
    // Verify default values
    verificationStep("Verify that the initial data variables are present in the comparison table");
    Assert.assertEquals("The default data variables are incorrect in the table", expectedVariables,
        comparisonReportPage.getActiveView().getDataVariables());

    EditComparisonReportPage editComparisonReportPage = 
        (EditComparisonReportPage) comparisonReportPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel dataPanel = editComparisonReportPage.getActiveView().getDataPanel();

    verificationStep("Verify that all the data variables are selected");
    Assert.assertEquals(
        "The active data variables are not the expected", expectedVariables.stream()
            .map(dataVariable -> dataVariable.getFullName()).collect(Collectors.toList()),
        dataPanel.getSelectedElements());

    dataPanel.clickElement(DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024.getFullName());

    expectedVariables.remove(DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024);

    verificationStep("Verify that the data variable is unchecked");
    Assert.assertEquals(
        "The active data variables are not the expected", expectedVariables.stream()
            .map(dataVariable -> dataVariable.getFullName()).collect(Collectors.toList()),
        dataPanel.getSelectedElements());

    comparisonReportPage = (ComparisonReportPage) editComparisonReportPage.clickDone();

    verificationStep("Verify that the expected data variables are present in the comparison table");
    Assert.assertEquals("The data variables are incorrect in the table", expectedVariables,
        comparisonReportPage.getActiveView().getDataVariables());
  }

  @Test
  public void testNoneVariable() {

    EditComparisonReportPage editComparisonReportPage = 
        (EditComparisonReportPage) comparisonReportPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel dataPanel = editComparisonReportPage.getActiveView().getDataPanel();

    dataPanel.clickElement(DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024.getFullName());
    dataPanel.clickElement(defaultDataVariables.get(0).getFullName());
    dataPanel.clickElement(defaultDataVariables.get(1).getFullName());

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editComparisonReportPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the data variables is selected");
    Assert.assertEquals("The active data variables are not the expected", new ArrayList<Location>(),
        dataPanel.getSelectedElements());

    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.DATA_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.DATA_MISSING,
        editComparisonReportPage.getActiveView().getErrorMessage());
  }

  @Test
  public void testClearAndSelectAllVariables() {

    EditComparisonReportPage editComparisonReportPage = 
        (EditComparisonReportPage) comparisonReportPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel dataPanel = editComparisonReportPage.getActiveView().getDataPanel();

    dataPanel.clickClear();

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editComparisonReportPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the data variables is selected");
    Assert.assertEquals("The active data variables are not the expected",
        new ArrayList<DataVariable>(), dataPanel.getSelectedElements());

    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.DATA_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.DATA_MISSING,
        editComparisonReportPage.getActiveView().getErrorMessage());

    dataPanel.clickSelectAll();

    verificationStep("Verify that all the data variables are selected");
    Assert.assertEquals(
        "The active data variables are not the expected", expectedVariables.stream()
            .map(dataVariable -> dataVariable.getFullName()).collect(Collectors.toList()),
        dataPanel.getSelectedElements());

    comparisonReportPage = (ComparisonReportPage) editComparisonReportPage.clickDone();

    verificationStep("Verify that all the data variables appear in the comparison table");
    Assert.assertEquals("The data variables are incorrect in the table", expectedVariables,
        comparisonReportPage.getActiveView().getDataVariables());
  }
}
