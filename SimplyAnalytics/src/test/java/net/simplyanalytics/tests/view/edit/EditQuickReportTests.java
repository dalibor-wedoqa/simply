package net.simplyanalytics.tests.view.edit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditQuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EditQuickReportTests extends TestBase {

  private ViewType view = ViewType.QUICK_REPORT;
  private final List<String> DEFAULT_LOCATIONS = new ArrayList<>(
      Arrays.asList(Location.LOS_ANGELES_CA_CITY.getName(), Location.USA.getName(),
          Location.CHICAGO_IL_CITY.getName(), Location.MIAMI_FL_CITY.getName(),
          Location.CLEVELAND_OH_CITY.getName(), Location.SAN_ANTONIO_CITY.getName(),
          Location.LOS_BANOS_CITY.getName()));

  private QuickReportPage quickReportPage;

  /**
   * Signing in, creating new project and adding Quick Report view.
   */
  @Before
  public void createProjectWithComparisonReportView() {

    driver.manage().window().maximize();

    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    quickReportPage = (QuickReportPage) mapPage.getViewChooserSection()
        .clickView(ViewType.QUICK_REPORT.getDefaultName());
    quickReportPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.CHICAGO_IL_CITY);
    quickReportPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.MIAMI_FL_CITY);
    quickReportPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.CLEVELAND_OH_CITY);
    quickReportPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.SAN_ANTONIO_CITY);
    quickReportPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.LOS_BANOS_CITY);

    quickReportPage = new QuickReportPage(driver);
  }

  @Test
  public void testEditLocation() {

    // Verify default values
    verificationStep("Verify that the initial locations are present in the comparison table");
    Assert.assertEquals("The default locations are incorrect in the table", DEFAULT_LOCATIONS,
        quickReportPage.getActiveView().getColumnHeadersWithScroll());

    EditQuickReportPage editQuickReportPage = (EditQuickReportPage) quickReportPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editQuickReportPage.getActiveView().getLocationsPanel();

    verificationStep("Verify that all the locations are selected");
    Assert.assertEquals("The active locations are not the expected",
        DEFAULT_LOCATIONS.stream().collect(Collectors.toList()),
        locationPanel.getSelectedElements());

    locationPanel.clickElement(Location.LOS_ANGELES_CA_CITY.getName());

    DEFAULT_LOCATIONS.remove(Location.LOS_ANGELES_CA_CITY.getName());

    verificationStep("Verify that the location is unchecked");
    Assert.assertEquals("The active locations are not the expected",
        DEFAULT_LOCATIONS.stream().collect(Collectors.toList()),
        locationPanel.getSelectedElements());

    quickReportPage = (QuickReportPage) editQuickReportPage.clickDone();

    verificationStep("Verify that the expected locations are present in the comparison table");
    Assert.assertEquals("The locations are incorrect in the table", DEFAULT_LOCATIONS,
        quickReportPage.getActiveView().getColumnHeadersWithScroll());
  }

  @Test
  public void testNoneLocation() {

    EditQuickReportPage editQuickReportPage = (EditQuickReportPage) quickReportPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editQuickReportPage.getActiveView().getLocationsPanel();

    locationPanel.clickElement(Location.LOS_ANGELES_CA_CITY.getName());
    locationPanel.clickElement(Location.USA.getName());
    locationPanel.clickElement(Location.CHICAGO_IL_CITY.getName());
    locationPanel.clickElement(Location.MIAMI_FL_CITY.getName());
    locationPanel.clickElement(Location.CLEVELAND_OH_CITY.getName());
    locationPanel.clickElement(Location.SAN_ANTONIO_CITY.getName());
    locationPanel.clickElement(Location.LOS_BANOS_CITY.getName());

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editQuickReportPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the locations are selected");
    Assert.assertEquals("The active locations are not the expected", new ArrayList<Location>(),
        locationPanel.getSelectedElements());

    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.LOCATION_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.LOCATION_MISSING,
        editQuickReportPage.getActiveView().getErrorMessage());
  }

  @Test
  public void testClearAndSelectAllLocation() {

    EditQuickReportPage editQuickReportPage = (EditQuickReportPage) quickReportPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();
    CheckboxContainerPanel locationPanel = editQuickReportPage.getActiveView().getLocationsPanel();

    locationPanel.clickClear();

    verificationStep("Verify that the Done button is disabled");
    Assert.assertTrue("The Done button should be disabled",
        editQuickReportPage.getActiveView().isDoneButtonDisabled());

    verificationStep("Verify that none of the locations are selected");
    Assert.assertEquals("The active locations are not the expected", new ArrayList<Location>(),
        locationPanel.getSelectedElements());

    verificationStep(
        "Verify that the error message appears: \"" + EditViewWarning.LOCATION_MISSING + "\"");
    Assert.assertEquals("The error message is not the expected", EditViewWarning.LOCATION_MISSING,
        editQuickReportPage.getActiveView().getErrorMessage());

    locationPanel.clickSelectAll();

    verificationStep("Verify that all the locations are selected");
    Assert.assertEquals("The active locations are not the expected",
        DEFAULT_LOCATIONS.stream().collect(Collectors.toList()),
        locationPanel.getSelectedElements());

    quickReportPage = (QuickReportPage) editQuickReportPage.clickDone();

    verificationStep("Verify that all the locations appear in the comparison table");
    Assert.assertEquals("The locations are incorrect in the table", DEFAULT_LOCATIONS,
        quickReportPage.getActiveView().getColumnHeadersWithScroll());
  }

}
