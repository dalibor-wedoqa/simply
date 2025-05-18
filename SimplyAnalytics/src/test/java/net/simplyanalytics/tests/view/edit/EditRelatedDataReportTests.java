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
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EditRelatedDataReportTests extends TestBase {

  private RelatedDataReportPage relatedDataReportPage;
  private final ViewType view = ViewType.RELATED_DATA;

  private final List<Location> defaultLocations = new ArrayList<>(
      Arrays.asList(Location.LOS_ANGELES_CA_CITY, Location.USA, Location.CHICAGO_IL_CITY,
          Location.MIAMI_FL_CITY));

  /**
   * Signing in and creating new project.
   * Open the related data report page.
   * Adding more locations.
   */
  @Before
  public void createProjectWithRelatedDataReportView() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();

    newViewPage.getActiveView().clickCreate(view).getActiveView().clickDoneButton();
    relatedDataReportPage = new RelatedDataReportPage(driver);

    relatedDataReportPage.getLdbSection().getLocationsTab()
        .voidChooseLocation(Location.CHICAGO_IL_CITY);
    relatedDataReportPage.getLdbSection().getLocationsTab()
        .voidChooseLocation(Location.MIAMI_FL_CITY);

    DataByCategoryDropwDown dataByCategoryBasePanel = ((DataByCategoryPanel) relatedDataReportPage
        .getLdbSection().getDataTab().getBrowsePanel())
            .clickOnACategoryData(CategoryData.POPULATION);
    dataByCategoryBasePanel.clickOnADataResult(DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2021);
    relatedDataReportPage = (RelatedDataReportPage) dataByCategoryBasePanel
        .clickClose(Page.RELATED_DATA_VIEW);

  }

  @Test
  public void testEditLocation() {
    // Verify default values
    verificationStep("Verify that the initial locations are present in the relatedData table");
    Assert.assertEquals("The default locations are incorrect in the table", defaultLocations,
        relatedDataReportPage.getActiveView().getLocations());

    EditRelatedDataReportPage editRelatedDataReportPage = 
        (EditRelatedDataReportPage) relatedDataReportPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editRelatedDataReportPage.getActiveView()
        .getLocationsPanel();

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

    relatedDataReportPage = (RelatedDataReportPage) editRelatedDataReportPage.clickDone();

    verificationStep("Verify that the expected location are present in the relatedData table");
    Assert.assertEquals("The locations are incorrect in the table", defaultLocations,
        relatedDataReportPage.getActiveView().getLocations());
  }

  @Test
  public void testNoneLocation() {

    EditRelatedDataReportPage editRelatedDataReportPage = 
        (EditRelatedDataReportPage) relatedDataReportPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editRelatedDataReportPage.getActiveView()
        .getLocationsPanel();

    locationPanel.clickElement(Location.LOS_ANGELES_CA_CITY.getName());
    locationPanel.clickElement(Location.USA.getName());
    locationPanel.clickElement(Location.CHICAGO_IL_CITY.getName());
    locationPanel.clickElement(Location.MIAMI_FL_CITY.getName());

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editRelatedDataReportPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the locations is selected");
    Assert.assertEquals("The active locations are not the expected", new ArrayList<Location>(),
        locationPanel.getSelectedElements());

    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.LOCATION_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.LOCATION_MISSING,
        editRelatedDataReportPage.getActiveView().getErrorMessage());
  }

  @Test
  public void testClearAndSelectAllLocation() {

    EditRelatedDataReportPage editRelatedDataReportPage = 
        (EditRelatedDataReportPage) relatedDataReportPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editRelatedDataReportPage.getActiveView()
        .getLocationsPanel();

    locationPanel.clickClear();

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editRelatedDataReportPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the locations is selected");
    Assert.assertEquals("The active locations are not the expected", new ArrayList<Location>(),
        locationPanel.getSelectedElements());

    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.LOCATION_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.LOCATION_MISSING,
        editRelatedDataReportPage.getActiveView().getErrorMessage());

    locationPanel.clickSelectAll();

    verificationStep("Verify that all the location are selected");
    Assert.assertEquals("The active locations are not the expected",
        Arrays.asList(defaultLocations.stream().map(location -> location.getName()).toArray()),
        locationPanel.getSelectedElements());

    relatedDataReportPage = (RelatedDataReportPage) editRelatedDataReportPage.clickDone();

    verificationStep("Verify that all the locations appear on the Related Data table");
    Assert.assertEquals("The locations are incorrect in the table", defaultLocations,
        relatedDataReportPage.getActiveView().getLocations());
  }

  @Test
  public void testEditVariable() {
    DataVariable lastData = DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024;
    final DataVariable editedData = 
        DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024;
    // Verify default values
    verificationStep(
        "Verify that the lastly added data variable is present in the related data toolbar");
    Assert.assertEquals("The data variable is incorrect in the toolbar", lastData,
        relatedDataReportPage.getToolbar().getActiveDataVariable());

    EditRelatedDataReportPage editRelatedDataReportPage = 
        (EditRelatedDataReportPage) relatedDataReportPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    RadioButtonContainerPanel dataPanel = editRelatedDataReportPage.getActiveView().getDataPanel();

    verificationStep("Verify that last data variable is the selected one");
    Assert.assertEquals("The active data variable is not the expected", lastData.getFullName(),
        dataPanel.getSelectedElement());

    dataPanel.clickElement(editedData.getFullName());

    verificationStep("Verify that the data variable is selected");
    Assert.assertEquals("The active data variable is not the expected", editedData.getFullName(),
        dataPanel.getSelectedElement());

    relatedDataReportPage = (RelatedDataReportPage) editRelatedDataReportPage.clickDone();

    verificationStep(
        "Verify that the expected data variable is present in the related data toolbar");
    Assert.assertEquals("The data variables are incorrect in the table", editedData,
        relatedDataReportPage.getToolbar().getActiveDataVariable());
  }
}
