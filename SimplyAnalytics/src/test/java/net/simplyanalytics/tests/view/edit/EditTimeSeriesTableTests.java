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
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditTimeSeriesPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EditTimeSeriesTableTests extends TestBase {

  private TimeSeriesPage timeSeriesPage;
  private final ViewType view = ViewType.TIME_SERIES;

  private final List<Location> defaultLocations = new ArrayList<>(
          Arrays.asList(Location.LOS_ANGELES_CA_CITY,
                  Location.USA,
                  Location.CHICAGO_IL_CITY,
                  Location.MIAMI_FL_CITY));

  /**
   * Signing in, creating new project.
   */
  @Before
  public void createProjectWithTimeSeriesTableView() {

    driver.manage().window().maximize();

    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
            InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
            .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);

    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();

    newViewPage.getActiveView().clickCreate(view).getActiveView().clickDoneButton();
    timeSeriesPage = new TimeSeriesPage(driver);

    timeSeriesPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.CHICAGO_IL_CITY);
    timeSeriesPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.MIAMI_FL_CITY);

    DataByCategoryDropwDown dataByCategoryBasePanel = ((DataByCategoryPanel) timeSeriesPage
            .getLdbSection().getDataTab().getBrowsePanel())
            .clickOnACategoryData(CategoryData.POPULATION);
    dataByCategoryBasePanel.clickOnADataResult(DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024);
    timeSeriesPage = (TimeSeriesPage) dataByCategoryBasePanel.clickClose(Page.TIME_SERIES_VIEW);

  }

  @Test
  public void testEditLocation() {
    // Verify default values
    verificationStep("Verify that the initial locations are present in the TimeSeries table");
    Assert.assertEquals("The default locations are incorrect in the table", defaultLocations,
            timeSeriesPage.getActiveView().getLocations());

    EditTimeSeriesPage editTimeSeriesPage = (EditTimeSeriesPage) timeSeriesPage
            .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editTimeSeriesPage.getActiveView().getLocationsPanel();

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

    timeSeriesPage = (TimeSeriesPage) editTimeSeriesPage.clickDone();

    verificationStep("Verify that the expected location are present in the TimSeries table");
    Assert.assertEquals("The locations are incorrect in the table", defaultLocations,
            timeSeriesPage.getActiveView().getLocations());
  }

  @Test
  public void testNoneLocation() {

    EditTimeSeriesPage editTimeSeriesPage = (EditTimeSeriesPage) timeSeriesPage
            .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editTimeSeriesPage.getActiveView().getLocationsPanel();

    locationPanel.clickElement(Location.LOS_ANGELES_CA_CITY.getName());
    locationPanel.clickElement(Location.USA.getName());
    locationPanel.clickElement(Location.CHICAGO_IL_CITY.getName());
    locationPanel.clickElement(Location.MIAMI_FL_CITY.getName());

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
            editTimeSeriesPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the locations is selected");
    Assert.assertEquals("The active locations are not the expected", new ArrayList<Location>(),
            locationPanel.getSelectedElements());

    verificationStep(
            "Verify that the error message appears: \"" + EditViewWarning.LOCATION_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.LOCATION_MISSING,
            editTimeSeriesPage.getActiveView().getErrorMessage());
  }

  @Test
  public void testClearAndSelectAllLocation() {

    EditTimeSeriesPage editTimeSeriesPage = (EditTimeSeriesPage) timeSeriesPage
            .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editTimeSeriesPage.getActiveView().getLocationsPanel();

    locationPanel.clickClear();

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
            editTimeSeriesPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the locations is selected");
    Assert.assertEquals("The active locations are not the expected", new ArrayList<Location>(),
            locationPanel.getSelectedElements());

    verificationStep(
            "Verify that the error message appears: \"" + EditViewWarning.LOCATION_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.LOCATION_MISSING,
            editTimeSeriesPage.getActiveView().getErrorMessage());

    locationPanel.clickSelectAll();

    verificationStep("Verify that all the location are selected");
    Assert.assertEquals("The active locations are not the expected",
            Arrays.asList(defaultLocations.stream().map(location -> location.getName()).toArray()),
            locationPanel.getSelectedElements());

    timeSeriesPage = (TimeSeriesPage) editTimeSeriesPage.clickDone();

    verificationStep("Verify that all the locations appear on the Time Series table");
    Assert.assertEquals("The locations are incorrect in the table", defaultLocations,
            timeSeriesPage.getActiveView().getLocations());
  }

  @Test
  public void testEditVariable() {
    System.out.println("debugg");
    DataVariable lastData = DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024;
    final DataVariable editedData =
            DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024;

    System.out.println("timeSeriesPage.getToolbar().getActiveDataVariable());\n" +
            timeSeriesPage.getToolbar().getActiveDataVariable());

    // Verify default values
    verificationStep("Verify that the lastly added data variable is present in the related data toolbar");
    Assert.assertEquals("The data variable is incorrect in the toolbar", lastData,
        timeSeriesPage.getToolbar().getActiveDataVariable());

    EditTimeSeriesPage editTimeSeriesPage = (EditTimeSeriesPage) timeSeriesPage
            .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    RadioButtonContainerPanel dataPanel = editTimeSeriesPage.getActiveView().getDataPanel();

    verificationStep("Verify that last data variable is the selected one");
    Assert.assertEquals("The active data variable is not the expected",
            lastData.getFullName(),
            dataPanel.getSelectedElement());

    dataPanel.clickElement(editedData.getFullName());

    verificationStep("Verify that the data variable is selected");
    Assert.assertEquals("The active data variable is not the expected",
            editedData.getFullName(),
            dataPanel.getSelectedElement());

    timeSeriesPage = (TimeSeriesPage) editTimeSeriesPage.clickDone();

    System.out.println("timeSeriesPage.getToolbar().getActiveDataVariable());\n" +
            timeSeriesPage.getToolbar().getActiveDataVariableFullName());

    verificationStep("Verify that the expected data variable is present in the Time Series table toolbar");

    // Ensure Assert statement is within the same block as the variable declaration
    Assert.assertEquals("The data variables are incorrect in the table",
            editedData,
            timeSeriesPage.getToolbar().getActiveDataVariableFullName());

//    System.out.println("==========================================================================================\n");
//
//    // Convert editedData to string
//    String verificationEditedData = String.valueOf(editedData);
//
//    System.out.println("editedData: \n" + verificationEditedData);
//    System.out.println("==========================================================================================\n");
//
//    // Convert the result to a string using String.valueOf()
//    String verificationData = String.valueOf(timeSeriesPage.getToolbar().getActiveDataVariable());
//
//    System.out.println("verificationData: \n" + verificationData);
//    System.out.println("==========================================================================================\n");
//
//// Ensure Assert statement is within the same block as the variable declaration
//    Assert.assertEquals("The data variables are incorrect in the table",
//            verificationEditedData,
//            verificationData);
//
  }
}
