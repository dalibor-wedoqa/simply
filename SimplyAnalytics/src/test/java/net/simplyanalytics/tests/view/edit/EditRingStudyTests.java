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
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class EditRingStudyTests extends TestBase {

  private RingStudyPage ringStudyPage;
  private final ViewType view = ViewType.RING_STUDY;

  private final List<DataVariable> DEFAULT_VARIABLES = new ArrayList<>(
		  Arrays.asList(
		  DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024,
		  DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024,
		  DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024));

  /**
   * Signing in and creating new project.
   * Open the ring study page.
   * Adding more locations.
   */
  @Before
  public void createProjectWithRingStudyView() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();

    newViewPage.getActiveView().clickCreate(ViewType.RING_STUDY).getActiveView().clickDoneButton();
    ringStudyPage = new RingStudyPage(driver);

    ringStudyPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.CHICAGO_IL_CITY);
    ringStudyPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.MIAMI_FL_CITY);

    DataByCategoryDropwDown dataByCategoryBasePanel = ((DataByCategoryPanel) ringStudyPage
        .getLdbSection().getDataTab().getBrowsePanel())
            .clickOnACategoryData(CategoryData.POPULATION);
    dataByCategoryBasePanel.clickOnADataResult(DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024);
    ringStudyPage = (RingStudyPage) dataByCategoryBasePanel.clickClose(Page.RING_STUDY_VIEW);

  }

  @Test
  public void testEditLocation() {
    Location lastLocation = Location.MIAMI_FL_CITY;
    Location editedLocation = Location.CHICAGO_IL_CITY;
    // Verify default values
    verificationStep("Verify that the last given location is present in the ring study toolbar");
    Assert.assertEquals("The default location is incorrect in the table", lastLocation,
        ringStudyPage.getToolbar().getActiveLocation());

    EditRingStudyPage editRingStudyPage = (EditRingStudyPage) ringStudyPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    RadioButtonContainerPanel locationPanel = editRingStudyPage.getActiveView().getLocationsPanel();

    verificationStep("Verify that last location is the selected one");
    Assert.assertEquals("The active location is not the expected", lastLocation.getName(),
        locationPanel.getSelectedElement());

    locationPanel.clickElement(editedLocation.getName());

    verificationStep("Verify that the location is selected");
    Assert.assertEquals("The active location is not the expected", editedLocation.getName(),
        locationPanel.getSelectedElement());

    ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();

    verificationStep("Verify that the expected location is present in the ring study toolbar");
    Assert.assertEquals("The location is incorrect in the table", editedLocation,
        ringStudyPage.getToolbar().getActiveLocation());
  }

  @Test
  public void testEditVariable() {
    // Verify default values
    verificationStep("Verify that the initial data variables are present in the ring study table");
    Assert.assertEquals("The default data variables are incorrect in the table", DEFAULT_VARIABLES,
        ringStudyPage.getActiveView().getDataVariables());

    EditRingStudyPage editRingStudyPage = (EditRingStudyPage) ringStudyPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel dataPanel = editRingStudyPage.getActiveView().getDataPanel();

    verificationStep("Verify that all the data variables are selected");
    Assert.assertEquals(
        "The active data variables are not the expected", DEFAULT_VARIABLES.stream()
            .map(dataVariable -> dataVariable.getFullName()).collect(Collectors.toList()),
        dataPanel.getSelectedElements());

    dataPanel.clickElement(DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024.getFullName());

    DEFAULT_VARIABLES.remove(DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024);

    verificationStep("Verify that the data variable is unchecked");
    Assert.assertEquals(
        "The active data variables are not the expected", DEFAULT_VARIABLES.stream()
            .map(dataVariable -> dataVariable.getFullName()).collect(Collectors.toList()),
        dataPanel.getSelectedElements());

    ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();

    verificationStep("Verify that the expected data variables are present in the ring study table");
    Assert.assertEquals("The data variables are incorrect in the table", DEFAULT_VARIABLES,
        ringStudyPage.getActiveView().getDataVariables());
  }

  @Test
  public void testNoneVariable() {

    EditRingStudyPage editRingStudyPage = (EditRingStudyPage) ringStudyPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel dataPanel = editRingStudyPage.getActiveView().getDataPanel();

    dataPanel.clickElement(DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024.getFullName());
    dataPanel.clickElement(defaultDataVariables.get(0).getFullName());
    dataPanel.clickElement(defaultDataVariables.get(1).getFullName());

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editRingStudyPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the data variables is selected");
    Assert.assertEquals("The active data variables are not the expected", new ArrayList<Location>(),
        dataPanel.getSelectedElements());

    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.DATA_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.DATA_MISSING,
        editRingStudyPage.getActiveView().getErrorMessage());
  }

  @Test
  public void testClearAndSelectAllVariables() {

    EditRingStudyPage editRingStudyPage = (EditRingStudyPage) ringStudyPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel dataPanel = editRingStudyPage.getActiveView().getDataPanel();

    dataPanel.clickClear();

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editRingStudyPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the data variables is selected");
    Assert.assertEquals("The active data variables are not the expected",
        new ArrayList<DataVariable>(), dataPanel.getSelectedElements());

    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.DATA_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.DATA_MISSING,
        editRingStudyPage.getActiveView().getErrorMessage());

    dataPanel.clickSelectAll();

    verificationStep("Verify that all the data variables are selected");
    Assert.assertEquals(
        "The active data variables are not the expected", DEFAULT_VARIABLES.stream()
            .map(dataVariable -> dataVariable.getFullName()).collect(Collectors.toList()),
        dataPanel.getSelectedElements());

    ringStudyPage = (RingStudyPage) editRingStudyPage.clickDone();

    verificationStep("Verify that all the data variables appear in the ring study table");
    Assert.assertEquals("The data variables are incorrect in the table", DEFAULT_VARIABLES,
        ringStudyPage.getActiveView().getDataVariables());
  }
}