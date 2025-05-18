package net.simplyanalytics.tests.view.edit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class EditRankingTests extends TestBase {

  private RankingPage rankingPage;
  private final ViewType view = ViewType.RANKING;

  private final List<DataVariable> DEFAULT_VARIABLES = new ArrayList<>(
		  Arrays.asList(
		  DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024,
		  DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024,
		  DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024));
  
  /**
   * Signing in and creating new project.
   * Open the ranking page.
   * Adding more locations.
   */
  @Before
  public void createProjectWithRankingView() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    rankingPage = (RankingPage) mapPage.getViewChooserSection()
        .clickView(ViewType.RANKING.getDefaultName());
    rankingPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.CHICAGO_IL_CITY);
    rankingPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.MIAMI_FL_CITY);

    ((DataByCategoryPanel) rankingPage.getLdbSection().getDataTab().getBrowsePanel())
        .clickOnACategoryData(CategoryData.POPULATION)
        .clickOnADataResult(DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024);

  }

  @Test
  public void testEditLocation() {
    Location lastLocation = Location.MIAMI_FL_CITY;
    Location editedLocation = Location.CHICAGO_IL_CITY;
    // Verify default values
    verificationStep("Verify that the last given location is present in the ranking toolbar");
    Assert.assertEquals("The default location is incorrect in the table", lastLocation,
        rankingPage.getToolbar().getActiveLocation());

    EditRankingPage editRankingPage = (EditRankingPage) rankingPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    RadioButtonContainerPanel locationPanel = editRankingPage.getActiveView().getLocationsPanel();

    verificationStep("Verify that last location is the selected one");
    Assert.assertEquals("The active location is not the expected", lastLocation.getName(),
        locationPanel.getSelectedElement());

    locationPanel.clickElement(editedLocation.getName());

    verificationStep("Verify that the location is selected");
    Assert.assertEquals("The active location is not the expected", editedLocation.getName(),
        locationPanel.getSelectedElement());

    rankingPage = (RankingPage) editRankingPage.clickDone();

    verificationStep("Verify that the expected location is present in the ranking toolbar");
    Assert.assertEquals("The location is incorrect in the table", editedLocation,
        rankingPage.getToolbar().getActiveLocation());
  }

  @Test
  @DisplayName("Verify that Ranking Table view has all the data columns selected")
  @Description("The test creates a Ranking Table view. Then it checks if the selected data matches data represented in the table.")
  @Tag("Flaky")
  public void testEditVariable() {
    // Verify default values
    verificationStep("Verify that the initial data variables are present in the ranking table");
    Assert.assertEquals("The default data variables are incorrect in the table", DEFAULT_VARIABLES,
        rankingPage.getActiveView().getDataVariables());

    EditRankingPage editRankingPage = (EditRankingPage) rankingPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel dataPanel = editRankingPage.getActiveView().getDataPanel();

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

    rankingPage = (RankingPage) editRankingPage.clickDone();

    verificationStep("Verify that the expected data variables are present in the ranking table");
    Assert.assertEquals("The data variables are incorrect in the table", DEFAULT_VARIABLES,
        rankingPage.getActiveView().getDataVariables());
  }

  @Test
  public void testNoneVariable() {

    EditRankingPage editRankingPage = (EditRankingPage) rankingPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel dataPanel = editRankingPage.getActiveView().getDataPanel();

    dataPanel.clickElement(DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024.getFullName());
    dataPanel.clickElement(defaultDataVariables.get(0).getFullName());
    dataPanel.clickElement(defaultDataVariables.get(1).getFullName());

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editRankingPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the data variables is selected");
    Assert.assertEquals("The active data variables are not the expected", new ArrayList<Location>(),
        dataPanel.getSelectedElements());

    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.DATA_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.DATA_MISSING,
        editRankingPage.getActiveView().getErrorMessage());
  }

  @Test
  public void testClearAndSelectAllVariables() {

    EditRankingPage editRankingPage = (EditRankingPage) rankingPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel dataPanel = editRankingPage.getActiveView().getDataPanel();

    dataPanel.clickClear();

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editRankingPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the data variables is selected");
    Assert.assertEquals("The active data variables are not the expected",
        new ArrayList<DataVariable>(), dataPanel.getSelectedElements());

    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.DATA_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.DATA_MISSING,
        editRankingPage.getActiveView().getErrorMessage());

    dataPanel.clickSelectAll();

    verificationStep("Verify that all the data variables are selected");
    Assert.assertEquals(
        "The active data variables are not the expected", DEFAULT_VARIABLES.stream()
            .map(dataVariable -> dataVariable.getFullName()).collect(Collectors.toList()),
        dataPanel.getSelectedElements());

    rankingPage = (RankingPage) editRankingPage.clickDone();

    verificationStep("Verify that all the data variables appear in the ranking table");
    Assert.assertEquals("The data variables are incorrect in the table", DEFAULT_VARIABLES,
        rankingPage.getActiveView().getDataVariables());
  }
}
